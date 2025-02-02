package com.example.payment;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.database.DatabaseConnectionImpl;
import com.example.payment.email.EmailService;
import com.example.payment.email.EmailServiceImpl;
import com.example.payment.payments.PaymentApi;
import com.example.payment.payments.PaymentApiImpl;
import com.example.payment.payments.PaymentProcessor;


public class Main {
    public static void main(String[] args) {

        PaymentApi paymentApi = new PaymentApiImpl();
        DatabaseConnection databaseConnection = new DatabaseConnectionImpl();
        EmailService emailService = new EmailServiceImpl();

        PaymentProcessor paymentProcessor = new PaymentProcessor(paymentApi, databaseConnection, emailService);

        boolean paymentSuccessful = paymentProcessor.processPayment(100.0);
        System.out.println("Payment success: " + paymentSuccessful);
    }
}
