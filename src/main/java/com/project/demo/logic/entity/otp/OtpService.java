package com.project.demo.logic.entity.otp;

import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailInfo;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.property.Property;
import com.project.demo.logic.entity.property.PropertyRepository;
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
    private final PropertyRepository propertyRepository;
    private final Random random = new Random();

    Logger logger = Logger.getLogger(OtpService.class.getName());

    public OtpService(OtpRepository otpRepository, EmailService emailService, PropertyRepository propertyRepository) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.propertyRepository = propertyRepository;
    }

    public String generateOtp() {
        String email = "robertaraya382@gmail.com";
        String otp = String.valueOf(random.nextInt(999999));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        Otp otpEntity = new Otp();
        otpEntity.setOtpCode(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpiryTime(expiryTime);

        otpRepository.save(otpEntity);

        sendOtpEmail(email, otp);

        return otp;
    }

    public boolean validateOtp(String email, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByOtpCodeAndEmail(otpCode, email);

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            LocalDateTime now = LocalDateTime.now();

            if (otp.getExpiryTime().isAfter(now)) {
                otpRepository.delete(otp);
                return true;
            } else {
                otpRepository.delete(otp);
                return false;
            }
        }
        return false;
    }

    @Scheduled(fixedRate = 60000)
    public void cleanExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        List<Otp> expiredOtps = otpRepository.findExpiredOtps(now);
        otpRepository.deleteAll(expiredOtps);
        logger.info("Se han eliminado " + expiredOtps.size() + " OTPs expirados.");
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

    public void sendPasswordResetByEmail(String email) {
        sendPasswordResetOtpEmail(email);
    }

    private EmailDetails createEmailDetails(String email, String emailBody) {
        EmailInfo fromAddress = new EmailInfo("JBart", email);
        EmailInfo toAddress = new EmailInfo("User", email);
        String subject = "Codigo de verificacion";

        return new EmailDetails(fromAddress, toAddress, subject, emailBody);
    }

    private void sendPasswordResetOtpEmail(String otp) {
        Property propertyEmail = propertyRepository.findByName("Correo Sendgrid")
                .orElseThrow(() -> new RuntimeException("Property with name 'Correo Sendgrid' not found"));
        String email = propertyEmail.getParameter();

        String emailBody = "Tu código de verificación para recuperación de contraseña es: " + otp + "\n Este código expira en 10 minutos.";
        EmailDetails emailDetails = createEmailDetails(email, emailBody);
        try {
            emailService.sendEmail(emailDetails);
        } catch (IOException e) {
            logger.info("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }
}