package com.example.payment.payments;

public interface PaymentApi {
    PaymentApiResponse charge(String apiKey, double amount);
}
