package com.example.payment.payments;

public class PaymentApiImpl implements PaymentApi {
    @Override
    public PaymentApiResponse charge(String apiKey, double amount) {
        // Simulate the charge process (could return a successful or failed response)
        return new PaymentApiResponse(true); // Example: successful response
    }
}
