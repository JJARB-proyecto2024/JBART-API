package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.email.EmailSignUpBrand;
import com.project.demo.logic.entity.enums.RoleEnum;
import com.project.demo.logic.entity.enums.StatusEnum;
import com.project.demo.logic.entity.otp.OtpService;
import com.project.demo.logic.entity.otp.ValidateOtpRequest;
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

import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthRestController {


    private final  UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final  RoleRepository roleRepository;
    private final AuthenticationService authenticationService;
    private final EmailSignUpBrand emailSignUpBrand;
    private final JwtService jwtService;
    private final OtpService otpService;

    public AuthRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationService authenticationService, EmailSignUpBrand emailSignUpBrand, JwtService jwtService, OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
        this.emailSignUpBrand = emailSignUpBrand;
        this.jwtService = jwtService;
        this.otpService = otpService;
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
        emailSignUpBrand.buildEmailSigUpBrand(userBrand);

        return ResponseEntity.ok(userRepository.save(userBrand));
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
        try{
            Optional<User> request1 = userRepository.findByEmail(request.getEmail());
            if (request1.isPresent()) {
                String email = request.getEmail();
                otpService.generateOtp(email);
                return "OTP generado exitosamente y enviado a " + email;
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return HttpStatus.NOT_FOUND.toString();
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("permitAll")
    public boolean resetPassword(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail();
        String otpCode = request.getOtpCode();
        String newPassword = request.getNewPassword();

        boolean result = otpService.validateOtp(email, otpCode);

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

        boolean result = otpService.validateOtp(email, otpCode);

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