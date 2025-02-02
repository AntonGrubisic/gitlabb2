package com.example.payment.payments;

public class PaymentApiResponse {
    private boolean success;

    public PaymentApiResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
