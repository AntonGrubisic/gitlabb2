package com.example.payment.payments;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.email.EmailService;

public class PaymentProcessor {
    private static final String API_KEY = "sk_test_123456"; // Keep static API_KEY as per your original request
    private PaymentApi paymentApi;
    private DatabaseConnection databaseConnection;
    private EmailService emailService;

    // Constructor for dependency injection
    public PaymentProcessor(PaymentApi paymentApi, DatabaseConnection databaseConnection, EmailService emailService) {
        this.paymentApi = paymentApi;
        this.databaseConnection = databaseConnection;
        this.emailService = emailService;
    }

    // Process payment method
    public boolean processPayment(double amount) {
        // Anropar extern betaltjänst via interface
        PaymentApiResponse response = paymentApi.charge(API_KEY, amount);

        // Skriver till databas vid lyckad betalning
        if (response.isSuccess()) {
            databaseConnection.executeUpdate("INSERT INTO payments (amount, status) VALUES (" + amount + ", 'SUCCESS')");
        }

        // Skickar e-post vid lyckad betalning
        if (response.isSuccess()) {
            emailService.sendPaymentConfirmation("user@example.com", amount);
        }

        return response.isSuccess();
    }
}
