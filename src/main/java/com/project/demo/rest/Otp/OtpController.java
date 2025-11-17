package com.project.demo.rest.Otp;

import com.project.demo.logic.entity.otp.OtpService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate/{email}")
    public String generateOtp(@PathVariable String email) {
        return otpService.generateOtp(email);
    }

    @PostMapping("/validate")
    public boolean validateOtp(@RequestBody String email, @RequestBody String otp) {
        return otpService.validateOtp(email,otp);
    }

    @PostConstruct
    public void cleanExpiredOtpsOnStartup() {
        otpService.cleanExpiredOtps();
        System.out.println("Limpieza de OTPs expirados realizada al iniciar la aplicación.");
    }
}
