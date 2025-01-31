package com.example.payment.email;

public class EmailServiceImpl implements EmailService {
    @Override
    public void sendPaymentConfirmation(String email, double amount) {
        // Simulate sending an email
        System.out.println("Sent payment confirmation to " + email + " for amount: " + amount);
    }
}
