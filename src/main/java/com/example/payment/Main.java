package com.example.payment;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.database.DatabaseConnectionImpl;
import com.example.payment.email.EmailService;
import com.example.payment.email.EmailServiceImpl;
import com.example.payment.payments.PaymentApi;
import com.example.payment.payments.PaymentApiImpl;
import com.example.payment.payments.PaymentProcessor;

// Example of how to use the PaymentProcessor
public class Main {
    public static void main(String[] args) {
        // Mock implementations (or actual implementations in production)
        PaymentApi paymentApi = new PaymentApiImpl();
        DatabaseConnection databaseConnection = new DatabaseConnectionImpl();
        EmailService emailService = new EmailServiceImpl();

        // Create the PaymentProcessor with dependency injection
        PaymentProcessor paymentProcessor = new PaymentProcessor(paymentApi, databaseConnection, emailService);

        // Process a payment
        boolean paymentSuccessful = paymentProcessor.processPayment(100.0);
        System.out.println("Payment success: " + paymentSuccessful);
    }
}
