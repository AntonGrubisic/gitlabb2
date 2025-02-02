package com.example.payment.email;

public class EmailServiceImpl implements EmailService {
    @Override
    public void sendPaymentConfirmation(String email, double amount) {
        System.out.println("Sent payment confirmation to " + email + " for amount: " + amount);
    }
}
