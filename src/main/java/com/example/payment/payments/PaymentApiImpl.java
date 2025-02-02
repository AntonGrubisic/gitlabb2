package com.example.payment.payments;

public class PaymentApiImpl implements PaymentApi {
    @Override
    public PaymentApiResponse charge(String apiKey, double amount) {
        return new PaymentApiResponse(true);
    }
}
