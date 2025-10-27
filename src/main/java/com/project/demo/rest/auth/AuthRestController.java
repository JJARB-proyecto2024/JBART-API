package com.project.demo.rest.auth;

import com.project.demo.logic.entity.otp.Otp;
import com.project.demo.logic.entity.otp.OtpRepository;
import com.project.demo.logic.entity.otp.OtpService;
import com.project.demo.logic.entity.otp.ValidateOtpRequest;
import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailInfo;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.enums.RoleEnum;
import com.project.demo.logic.entity.enums.StatusEnum;
import com.project.demo.logic.entity.property.Property;
import com.project.demo.logic.entity.property.PropertyRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RequestMapping("/auth")
@RestController
public class AuthRestController {


    private final  UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final  RoleRepository roleRepository;
    private final  PropertyRepository propertyRepository;
    private final  EmailService emailService;
    private final  OtpRepository otpRepository;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final OtpService opOtpService;

    private final Random random = new Random();

    public AuthRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PropertyRepository propertyRepository, EmailService emailService, OtpRepository otpRepository, AuthenticationService authenticationService, JwtService jwtService, OtpService opOtpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.propertyRepository = propertyRepository;
        this.emailService = emailService;
        this.otpRepository = otpRepository;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.opOtpService = opOtpService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) {
        try {
            User authenticatedUser = authenticationService.authenticate(user);

            if (!StatusEnum.ACTIVE.equals(authenticatedUser.getStatus())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponse("Cuenta inactiva. Por favor, ve al enlace de activación de cuenta."));
            }

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            User foundedUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            loginResponse.setAuthUser(foundedUser);

            return ResponseEntity.ok(loginResponse);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new LoginResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Error de autenticación. Por favor, verifica tus credenciales."));
        }
    }

    @PostMapping("/signup/brand")
    public ResponseEntity<?> registerUserBrand(@RequestBody UserBrand userBrand) {
        userBrand.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        Role role = roleRepository.findByName(RoleEnum.USER_BRAND)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        userBrand.setRole(role);
        userBrand.setStatus(StatusEnum.INACTIVE);

        sendStatusUpdateEmail(userBrand.getBrandName(), buildEmailBody(userBrand.getBrandName()));

        return ResponseEntity.ok(userRepository.save(userBrand));
    }

    private void sendStatusUpdateEmail(String name, String emailBody) {
        EmailDetails emailDetails = createEmailDetails(name, emailBody);
        try {
            emailService.sendEmail(emailDetails);
            System.out.println("El correo se envio con exito.");
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

    @PostMapping("/signup/buyer")
    public ResponseEntity<?> registerUserBuyer(@RequestBody UserBuyer userBuyer) {
        userBuyer.setPassword(passwordEncoder.encode(userBuyer.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }
        userBuyer.setRole(optionalRole.get());
        userBuyer.setStatus(StatusEnum.ACTIVE);
        UserBuyer savedUser = userRepository.save(userBuyer);
        return ResponseEntity.ok(savedUser);
    }



    @PostMapping("/generatePasswordResetOtp")
    @PreAuthorize("permitAll")
    public String generatePasswordResetOtp(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail();
        String otp = String.valueOf(random.nextInt(999999));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        Otp otpEntity = new Otp();
        otpEntity.setOtpCode(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpiryTime(expiryTime);

        otpRepository.save(otpEntity);

        opOtpService.sendPasswordResetByEmail(otp);

        return "OTP generado exitosamente y enviado a " + email;
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("permitAll")
    public boolean resetPassword(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail();
        String otpCode = request.getOtpCode();
        String newPassword = request.getNewPassword();

        boolean result = opOtpService.validateOtp(email, otpCode);

        if (result) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado para el email: " + email));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            } else {

                return false;

        }
    }

    @PostMapping("/resetStatusAccount")
    @PreAuthorize("permitAll")
    public boolean resetStatusAccount(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail();
        String otpCode = request.getOtpCode();

        boolean result = opOtpService.validateOtp(email, otpCode);

        if (result) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado para el email: " + email));
            user.setStatus(StatusEnum.ACTIVE);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

}