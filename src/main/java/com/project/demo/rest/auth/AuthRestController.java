package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailInfo;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthRestController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;



    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthRestController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = authenticationService.authenticate(user);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<User> foundedUser = userRepository.findByEmail(user.getEmail());

        foundedUser.ifPresent(loginResponse::setAuthUser);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }
        user.setRole(optionalRole.get());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/signup/brand")
    public ResponseEntity<?> registerUserBrand(@RequestBody UserBrand userBrand) {
        userBrand.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        Role role = roleRepository.findByName(RoleEnum.USER_BRAND)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        userBrand.setRole(role);
        userBrand.setStatus("Inactivo");

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
        userBrand.setRole(optionalRole.get());
        userBrand.setStatus("Inactivo");
        UserBrand savedUser = userRepository.save(userBrand);
        return ResponseEntity.ok(savedUser);
    }

    private EmailDetails createEmailDetails(String name, String emailBody) {
        String email = "robertaraya382@gmail.com";
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
        userBuyer.setStatus("Activo");
        UserBuyer savedUser = userRepository.save(userBuyer);
        return ResponseEntity.ok(savedUser);
    }
}