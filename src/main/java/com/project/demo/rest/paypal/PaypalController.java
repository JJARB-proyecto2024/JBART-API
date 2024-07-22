package com.project.demo.rest.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.project.demo.logic.entity.paypal.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    private PaypalService paypalService;

    @PostMapping("/createPayment")
    public ResponseEntity<String> createPayment(
            @RequestBody List<ItemDto> items,
            @RequestHeader("host") String host) {
        try {
            String baseUrl = "http://" + host;
            Payment payment = paypalService.createPayment(items, baseUrl);
            return new ResponseEntity<>(payment.toString(), HttpStatus.CREATED);
        } catch (PayPalRESTException e) {
            // Log error details and return a more informative message
            e.printStackTrace();
            return new ResponseEntity<>("Error creating payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/executePayment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> executePayment(@RequestBody ExecutePaymentDto dto) {
        try {
            Payment payment = paypalService.excecutePayment(dto.getPaymentId(), dto.getPayerId());
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
