package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    // Create Order
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        return orderRepository.save(order);
    }

    // Update Order
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setProduct(updatedOrder.getProduct());
        order.setQuantity(updatedOrder.getQuantity());
        order.setPrice(updatedOrder.getPrice());
        order.setStatus(updatedOrder.getStatus());
        return orderRepository.save(order);
    }

    // Track Order
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Notify Customer After Payment
    public void notifyCustomerAfterPayment(Long orderId) {
        Order order = getOrder(orderId);
        if (!"PAID".equals(order.getStatus())) {
            throw new RuntimeException("Order is not paid yet");
        }
        notificationService.sendNotification(order.getUserId(), "Your payment was successful for order ID: " + orderId);
    }
}
