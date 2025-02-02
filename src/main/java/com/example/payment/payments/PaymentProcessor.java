package com.example.payment.payments;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.email.EmailService;

public class PaymentProcessor {
    private static final String API_KEY = "sk_test_123456";
    private final PaymentApi paymentApi;
    private final DatabaseConnection databaseConnection;
    private final EmailService emailService;

    public PaymentProcessor(PaymentApi paymentApi, DatabaseConnection databaseConnection, EmailService emailService) {
        this.paymentApi = paymentApi;
        this.databaseConnection = databaseConnection;
        this.emailService = emailService;
    }

    public boolean processPayment(double amount) {
        PaymentApiResponse response = paymentApi.charge(API_KEY, amount);
        if (response.isSuccess()) {
            databaseConnection.executeUpdate("INSERT INTO payments (amount, status) VALUES (" + amount + ", 'SUCCESS')");
            emailService.sendPaymentConfirmation("user@example.com", amount);
        } else {
            databaseConnection.executeUpdate("INSERT INTO payments (amount, status) VALUES (" + amount + ", 'FAILED')");
        }

        return response.isSuccess();
    }
}
