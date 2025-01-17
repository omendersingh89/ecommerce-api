package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.model.Payment;

interface PaymentRepository extends JpaRepository<Payment, Long> {
}
