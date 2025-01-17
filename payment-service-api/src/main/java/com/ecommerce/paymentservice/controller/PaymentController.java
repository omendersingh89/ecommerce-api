package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
class PaymentController {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        // Call CardValidationService to validate the card
        String cardValidationUrl = "http://localhost:8082/api/card/validate";
        ResponseEntity<String> validationResponse;

        try {
            validationResponse = restTemplate.postForEntity(cardValidationUrl, paymentRequest.getCardDetails(), String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: Card validation failed.");
        }

        // If card validation is successful
        if (validationResponse.getStatusCode() == HttpStatus.OK) {
            // Logic for payment processing
            double amount = Double.parseDouble(request.get("amount"));
            String currency = request.get("currency");
            String status = request.get("status");
            return paymentService.processPayment(amount, currency, status);
            return ResponseEntity.ok("Payment processed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: Invalid card details.");
        }
    }


    @GetMapping("/{id}")
    public Optional<Payment> getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }
}
