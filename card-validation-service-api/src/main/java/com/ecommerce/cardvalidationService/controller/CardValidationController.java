package com.ecommerce.cardvalidationService.controller;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/card-validation")
public class CardValidationService {

    // Endpoint to validate card details
    @PostMapping("/validate")
    public ResponseEntity<String> validateCard(@RequestParam String cardNumber, @RequestParam String pin) {
        // Validate card number and PIN
        if (validateCardNumber(cardNumber, pin)) {
            return ResponseEntity.ok("Card validation successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid card details or PIN");
        }
    }

    // Validate card number format
    @PostMapping("/validate-card-number")
    public ResponseEntity<Boolean> validateCardNumber(@RequestBody String cardNumber) {
        boolean isValid = cardNumber != null && cardNumber.matches("\\d{16}");
        return ResponseEntity.ok(isValid);
    }

    // Validate card expiration date (MM/YY format)
    @PostMapping("/validate-expiration-date")
    public ResponseEntity<Boolean> validateExpirationDate(@RequestBody String expirationDate) {
        if (expirationDate == null || !expirationDate.matches("\\d{2}/\\d{2}")) {
            return ResponseEntity.ok(false);
        }
        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        if (month < 1 || month > 12) {
            return ResponseEntity.ok(false);
        }

        YearMonth currentYearMonth = YearMonth.now();
        YearMonth cardYearMonth = YearMonth.of(2000 + year, month);

        boolean isValid = !cardYearMonth.isBefore(currentYearMonth);
        return ResponseEntity.ok(isValid);
    }

    // Validate CVV format (3 digits)
    @PostMapping("/validate-cvv")
    public ResponseEntity<Boolean> validateCVV(@RequestBody String cvv) {
        boolean isValid = cvv != null && cvv.matches("\\d{3}");
        return ResponseEntity.ok(isValid);
    }
}