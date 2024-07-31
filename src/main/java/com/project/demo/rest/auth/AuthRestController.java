package com.project.demo.rest.auth;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.project.demo.logic.entity.Otp.Otp;
import com.project.demo.logic.entity.Otp.OtpRepository;
import com.project.demo.logic.entity.Otp.ValidateOtpRequest;
import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.email.EmailDetails;
import com.project.demo.logic.entity.email.EmailInfo;
import com.project.demo.logic.entity.email.EmailService;
import com.project.demo.logic.entity.paypal.PaypalService;
import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.paypal.ExecutePaymentDto;
import com.project.demo.logic.entity.paypal.ItemDto;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
    private UserBrandRepository userBrandRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private UserBuyerRepository userBuyerRepository;
    private ProductRepository productRepository;
    

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


    // Endpoint para generar OTP y enviarlo por correo
    @PostMapping("/generatePasswordResetOtp")
    @PreAuthorize("permitAll")
    public String generatePasswordResetOtp(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail();
        String otpCode = request.getOtpCode();
        String newPassword = request.getNewPassword();
        String otp = String.valueOf(new Random().nextInt(999999));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10); // Expira en 10 minutos

        Otp otpEntity = new Otp();
        otpEntity.setOtpCode(otp);
        otpEntity.setEmail(email);
        otpEntity.setExpiryTime(expiryTime);

        otpRepository.save(otpEntity);

        sendPasswordResetOtpEmail(otp);

        return "OTP generado exitosamente y enviado a " + email;
    }

    // Endpoint para validar OTP y actualizar la contraseña
    @PostMapping("/resetPassword")
    @PreAuthorize("permitAll")
    public boolean resetPassword(@RequestBody ValidateOtpRequest request) {
        String email = request.getEmail(); // Correo electrónico fijo
        String otpCode = request.getOtpCode();
        String newPassword = request.getNewPassword();

        boolean result = validateOtp(email, otpCode);

        if (result) {
                // Actualizar la contraseña del usuario
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado para el email: " + email));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            } else {

                return false;

        }
    }

    public boolean validateOtp(String email, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByOtpCodeAndEmail(otpCode, email);

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            LocalDateTime now = LocalDateTime.now();

            if (otp.getExpiryTime().isAfter(now)) {
                // OTP válido y no expirado, procede con la validación
                otpRepository.delete(otp);
                return true;
            } else {
                // OTP expirado, eliminarlo de la base de datos (opcional)
                otpRepository.delete(otp);
                return false;
            }
        }

        // OTP no encontrado
        return false;
    }

    // Método para enviar correo electrónico con el OTP de recuperación de contraseña
    private void sendPasswordResetOtpEmail(String otp) {
        String email = "robertaraya382@gmail.com";
        String subject = "Recuperación de contraseña - Código de verificación";
        String emailBody = "Tu código de verificación para recuperación de contraseña es: " + otp + "\n Este código expira en 10 minutos.";
        EmailDetails emailDetails = createEmailDetails(emailBody);
        try {
            emailService.sendEmail(emailDetails);
            System.out.println("Correo electrónico con OTP de recuperación de contraseña enviado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 60000) // Ejecutar cada 1 minuto (ajustar según necesidad)
    public void cleanExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        List<Otp> expiredOtps = otpRepository.findExpiredOtps(now);
        otpRepository.deleteAll(expiredOtps);
        System.out.println("Se han eliminado " + expiredOtps.size() + " OTPs expirados.");
    }

    // Método para crear detalles de correo electrónico
    private EmailDetails createEmailDetails(String emailBody) {
        EmailInfo fromAddress = new EmailInfo("JBart", "robertaraya382@gmail.com"); // Ajustar según tu configuración
        EmailInfo toAddress = new EmailInfo("Usuario", "robertaraya382@gmail.com");
        String subject = "Recuperación de contraseña - Código de verificación";

        return new EmailDetails(fromAddress, toAddress, subject, emailBody);
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody List<ItemDto> items, @RequestHeader("host") String host) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserBuyer currentUser = (UserBuyer) authentication.getPrincipal();
            Long userId = currentUser.getId();

            Payment payment = paypalService.createPayment(items, "http://" + host, userId);
            String approvalLink = payment.getLinks().stream()
                    .filter(link -> "approval_url".equals(link.getRel()))
                    .findFirst()
                    .map(link -> link.getHref())
                    .orElseThrow(() -> new RuntimeException("Approval URL not found"));

            Map<String, String> response = new HashMap<>();
            response.put("id", payment.getId());
            response.put("token", approvalLink.substring(approvalLink.indexOf("token=") + 6));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping("/executePayment")
    public ResponseEntity<?> executePayment(@RequestBody ExecutePaymentDto dto) {
        try {
            return new ResponseEntity<>(paypalService.executePayment(dto.getPaymentId(), dto.getPayerId()), HttpStatus.OK);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error executing payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/brands")
    @PreAuthorize("permitAll")
    public List<UserBrand> getAllBrandActive() {
        return userBrandRepository.findUserBrandByStatusActive();
    }

    @GetMapping("/products")
    @PreAuthorize("permitAll")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}