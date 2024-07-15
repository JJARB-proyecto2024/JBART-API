package com.project.demo.logic.entity.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailUtilityService {
    @Autowired
    private EmailService emailService;

    public void sendStatusUpdateEmail(String name, String emailBody) {
        try {
            EmailDetails emailDetails = createEmailDetails(name, emailBody);
            emailService.sendEmail(emailDetails);
            System.out.println("El correo se envio con exito.");
        } catch (IOException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }

    private EmailDetails createEmailDetails(String name, String emailBody) {
        String email = "robertaraya382@gmail.com"; // Consider externalizing this
        EmailInfo fromAddress = new EmailInfo("JBart", email);
        EmailInfo toAddress = new EmailInfo(name, email);
        String subject = "Actualización de Estado"; // Consider making this dynamic based on context
        return new EmailDetails(fromAddress, toAddress, subject, emailBody);
    }
}
