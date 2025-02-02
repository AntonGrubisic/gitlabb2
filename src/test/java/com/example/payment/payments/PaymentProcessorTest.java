package com.example.payment.payments;

import com.example.payment.database.DatabaseConnection;
import com.example.payment.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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

}
