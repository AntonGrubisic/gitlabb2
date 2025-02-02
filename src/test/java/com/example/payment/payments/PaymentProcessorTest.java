package com.example.payment.payments;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentProcessorTest {
    private PaymentProcessor paymentProcessor;
    private PaymentApi paymentApiMock;
    private DatabaseConnection databaseConnectionMock;
    private EmailService emailServiceMock;

    @BeforeEach
    public void setUp() {
        paymentApiMock = mock(PaymentApi.class);
        databaseConnectionMock = mock(DatabaseConnection.class);
        emailServiceMock = mock(EmailService.class);

        paymentProcessor = new PaymentProcessor(paymentApiMock, databaseConnectionMock, emailServiceMock);
    }
    @Test
    public void testProcessPaymentIfSucceeds() {

        when(paymentApiMock.charge(anyString(), anyDouble())).thenReturn(new PaymentApiResponse(true));
        boolean result = paymentProcessor.processPayment(100.0);
        assertTrue(result, "Payment should be successful");
        verify(databaseConnectionMock).executeUpdate("INSERT INTO payments (amount, status) VALUES (100.0, 'SUCCESS')");
        verify(emailServiceMock).sendPaymentConfirmation("user@example.com", 100.0);
    }

}
