package com.project.demo.logic.entity.email;

import com.project.demo.logic.entity.property.Property;
import com.project.demo.logic.entity.property.PropertyRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailSignUpBrand {

    private final EmailService emailService;
    private final PropertyRepository propertyRepository;

    public EmailSignUpBrand(EmailService emailService, PropertyRepository propertyRepository) {
        this.emailService = emailService;
        this.propertyRepository = propertyRepository;
    }


    public void buildEmailSigUpBrand(UserBrand userBrand) {
        sendStatusUpdateEmail(userBrand.getBrandName(), buildEmailBody(userBrand.getBrandName()));
    }



    private void sendStatusUpdateEmail(String name, String emailBody) {
        EmailDetails emailDetails = createEmailDetails(name, emailBody);
        try {
            emailService.sendEmail(emailDetails);
        } catch (IOException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }

    private EmailDetails createEmailDetails(String name, String emailBody) {
        // Recuperar la propiedad "Correo Sendgrid" usando Optional
        Property property = propertyRepository.findByName("Correo Sendgrid")
                .orElseThrow(() -> new RuntimeException("Property with name 'Correo Sendgrid' not found"));

        // Obtener el parámetro (email) de la propiedad
        String email = property.getParameter();

        return new EmailDetails(new EmailInfo("JBart", email), new EmailInfo(name, email), "Actualización de Estado", emailBody);
    }

    private String buildEmailBody(String name) {
        return String.format("Hola %s,\n\nGracias por registrarte en nuestra plataforma. Tu solicitud ha sido recibida y será procesada en las próximas horas por un administrador para validar la veracidad de los datos registrados en el formulario de registro.\n\nSaludos,\nEquipo JBart", name);
    }
}
