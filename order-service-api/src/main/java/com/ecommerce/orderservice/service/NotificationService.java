package com.ecommerce.orderservice.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(Long userId, String message) {
        // Simulate sending notification (e.g., email, SMS)
        System.out.println("Notification sent to user " + userId + ": " + message);
    }
}
