package com.project.demo.logic.entity.otp;

import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailInfo;
import com.project.demo.logic.entity.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final Random random;

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 5;

    Logger logger = Logger.getLogger(OtpService.class.getName());

    public OtpService(OtpRepository otpRepository, EmailService emailService, Random random) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.random = random;
    }

    public String generateOtp(String email) {
        validateEmail(email);

        String otp = generateOtpCode();
        LocalDateTime expiryTime = calculateExpiryTime();

        Otp otpEntity = createOtpEntity(email, otp, expiryTime);
        otpRepository.save(otpEntity);

        sendOtpEmail(email, otp);

        logger.info("OTP generated for email: " + email);
        return otp;
    }

    public boolean validateOtp(String email, String otpCode) {
        validateEmail(email);
        validateOtpCode(otpCode);


        Optional<Otp> otpOptional = otpRepository.findByOtpCodeAndEmail(otpCode, email);

        if (otpOptional.isEmpty()) {
            logger.warning("OTP not found for email: " + email);
            return false;
        }

        Otp otp = otpOptional.get();
        boolean isValid = !isExpired(otp);

        otpRepository.delete(otp);

        if (isValid) {
            logger.info("OTP validated successfully for email: " + email);
        } else {
            logger.warning("OTP expired for email: " + email);
        }

        return isValid;
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    private void validateOtpCode(String otpCode) {
        if (otpCode == null) {
            throw new IllegalArgumentException("OTP code cannot be null");
        }
        String regex = "\\d{" + OTP_LENGTH +"}";
        if (!otpCode.matches(regex)) {
            throw new IllegalArgumentException("OTP code is not valid");
        }
    }

    protected String generateOtpCode() {
        int max = (int) Math.pow(10, OTP_LENGTH);
        String format = String.format("%%0%dd", OTP_LENGTH);
        int randomNumber = random.nextInt(max);

        return String.format(format, randomNumber);
    }

    private LocalDateTime calculateExpiryTime() {
        return LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
    }

    private Otp createOtpEntity(String email, String otp, LocalDateTime expiryTime) {
        Otp otpEntity = new Otp();
        otpEntity.setOtpCode(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpiryTime(expiryTime);
        return otpEntity;
    }

    private boolean isExpired(Otp otp) {
        return otp.getExpiryTime().isBefore(LocalDateTime.now())
                || otp.getExpiryTime().isEqual(LocalDateTime.now());
    }

    @Scheduled(fixedRate = 60000)
    public void cleanExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        List<Otp> expiredOtps = otpRepository.findExpiredOtps(now);
        otpRepository.deleteAll(expiredOtps);
        logger.info("The expiration of OTPs  " + expiredOtps.size() + " has been removed.");
    }

    private void sendOtpEmail(String email, String otp) {
        String emailBody = "Tu codigo de verificacion es: " + otp + "\n Este codigo expira en 10 minutos.";
        EmailDetails emailDetails = createEmailDetails(email, emailBody);
        try {
            emailService.sendEmail(emailDetails);
            logger.info("El correo se envio con exito.");
        } catch (IOException e) {
            logger.info("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }

    private EmailDetails createEmailDetails(String email, String emailBody) {
        EmailInfo fromAddress = new EmailInfo("JBart", email);
        EmailInfo toAddress = new EmailInfo("User", email);
        String subject = "Codigo de verificacion";

        return new EmailDetails(fromAddress, toAddress, subject, emailBody);
    }
}