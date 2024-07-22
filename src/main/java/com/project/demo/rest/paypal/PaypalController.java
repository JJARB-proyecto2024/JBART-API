package com.project.demo.rest.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.project.demo.logic.entity.paypal.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paypal")
@CrossOrigin(origins = "http://localhost:4200")
public class PaypalController {
    @Autowired
    private PaypalService paypalService;

    @PostMapping("/createPayment")
    public ResponseEntity<Payment> createPayment(
            @RequestBody List<ItemDto> items,
            @RequestHeader("host") String host)
    {
        try {
            String baseUrl = "http://" + host;
            Payment payment = paypalService.createPayment(items, baseUrl);
            return  new ResponseEntity<>(payment, HttpStatus.CREATED);
        }catch (PayPalRESTException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/executePayment")
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
