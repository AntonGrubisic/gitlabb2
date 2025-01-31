package com.example.payment.payments;

public class PaymentApiResponse {
    private boolean success;

    // Constructor
    public PaymentApiResponse(boolean success) {
        this.success = success;
    }

    // Getter for success
    public boolean isSuccess() {
        return success;
    }
}
