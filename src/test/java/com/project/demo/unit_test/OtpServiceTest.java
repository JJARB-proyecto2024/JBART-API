package com.project.demo.unit_test;

import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.otp.Otp;
import com.project.demo.logic.entity.otp.OtpRepository;
import com.project.demo.logic.entity.otp.OtpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OtpServiceTest {

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private Random random;

    @InjectMocks
    private OtpService otpService;


    private static final String TEST_EMAIL = "andreytest@gmail.com";
    private static final String VALID_OTP = "123456";
    private static final String INVALID_OTP = "12345";


    @Test
    void generateOtp_SendEmail_Success() throws IOException {
        when(random.nextInt(1000000)).thenReturn(123456);
        when(emailService.sendEmail(any(EmailDetails.class))).thenReturn(String.valueOf(true));
        when(otpRepository.save(any(Otp.class))).thenAnswer(i -> i.getArguments()[0]);

        String generateOtp = otpService.generateOtp(TEST_EMAIL);
        assertEquals(VALID_OTP, generateOtp);

        verify(otpRepository, times(1)).save(any(Otp.class));
        verify(emailService, times(1)).sendEmail(any(EmailDetails.class));

    }

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

        verify(otpRepository, times(1)).delete(mockOtp);
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
        verify(otpRepository, times(1)).delete(expiredOtp);
    }

    @Test
    void validateOtp_IsNotFound() {
        when(otpRepository.findByOtpCodeAndEmail(VALID_OTP, TEST_EMAIL))
                .thenReturn(Optional.empty());

        boolean isValid = otpService.validateOtp(TEST_EMAIL, VALID_OTP);
        assertFalse(isValid);

        verify(otpRepository, never()).delete(any(Otp.class));
    }

    @Test
    void validateOtp_FormatInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            otpService.validateOtp(TEST_EMAIL, INVALID_OTP);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            otpService.validateOtp(TEST_EMAIL, "abcdef");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            otpService.validateOtp(TEST_EMAIL, null);
        });

        verify(otpRepository, never()).findByOtpCodeAndEmail(anyString(), anyString());


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
