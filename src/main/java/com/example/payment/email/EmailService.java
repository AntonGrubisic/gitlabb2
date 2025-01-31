package com.example.payment.email;

public interface EmailService {
    void sendPaymentConfirmation(String email, double amount);
}
