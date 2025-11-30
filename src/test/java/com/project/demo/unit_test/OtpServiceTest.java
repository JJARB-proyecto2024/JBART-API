package com.project.demo.unit_test;

import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.otp.Otp;
import com.project.demo.logic.entity.otp.OtpRepository;
import com.project.demo.logic.entity.otp.OtpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OtpServiceTest {

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OtpService otpService;


    private static final String TEST_EMAIL = "andreytest@gmail.com";
    private static final String VALID_OTP = "123456";

    @Test
    void generateOtp_WhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            otpService.generateOtp(null);
        });

        verifyNoInteractions(otpRepository, emailService);
    }

    @Test
    void validateOtp_NotExpiredTime() {
        Otp mockOtp = new Otp();
        mockOtp.setEmail(TEST_EMAIL);
        mockOtp.setOtpCode(VALID_OTP);
        mockOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        when(otpRepository.findByOtpCodeAndEmail(VALID_OTP, TEST_EMAIL))
                .thenReturn(Optional.of(mockOtp));

        boolean isValid = otpService.validateOtp(TEST_EMAIL, VALID_OTP);
        assertTrue(isValid);
    }

    @Test
    void validateOtp_ExpiredTime() {
        Otp expiredOtp = new Otp();
        expiredOtp.setEmail(TEST_EMAIL);
        expiredOtp.setOtpCode(VALID_OTP);
        expiredOtp.setExpiryTime(LocalDateTime.now().minusMinutes(1));

        when(otpRepository.findByOtpCodeAndEmail(VALID_OTP, TEST_EMAIL))
        .thenReturn(Optional.of(expiredOtp));

        boolean isValid = otpService.validateOtp(TEST_EMAIL, VALID_OTP);

        assertFalse(isValid);
    }

    @Test
    void validateOtp_IsNotFound() {
        when(otpRepository.findByOtpCodeAndEmail(VALID_OTP, TEST_EMAIL))
                .thenReturn(Optional.empty());

        boolean isValid = otpService.validateOtp(TEST_EMAIL, VALID_OTP);
        assertFalse(isValid);
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"12345", "abcdef", ""})
    void validateOtp_FormatInvalid(String invalidOtp) {
        assertThrows(IllegalArgumentException.class, () -> {
            otpService.validateOtp(TEST_EMAIL, invalidOtp);
        });
    }

    @Test
    void cleanExpiredOtp() {
        List<Otp> expiredList = List.of(new Otp(), new Otp());

        when(otpRepository.findExpiredOtps(any(LocalDateTime.class)))
                .thenReturn(expiredList);

        otpService.cleanExpiredOtps();

        verify(otpRepository, times(1)).deleteAll(expiredList);
    }
}
