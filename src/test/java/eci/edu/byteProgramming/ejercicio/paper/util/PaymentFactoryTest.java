package eci.edu.byteProgramming.ejercicio.paper.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite para Payment Factory Pattern
 * Valida que cada factory cree correctamente los tipos de pago correspondientes.
 */
@DisplayName("Payment Factory Pattern Tests")
class PaymentFactoryTest {
    
    @Test
    @DisplayName("CreditCardPaymentFactory should create CreditCardFactory instance")
    void testCreditCardFactoryCreation() {
        PaymentFactory factory = new CreditCardPaymentFactory(
            "4532123456789012",
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10"
        );
        
        PaymentMethod payment = factory.createPaymentMethod(1200.00, "CUST001", "Payment");
        
        assertNotNull(payment);
        assertInstanceOf(CreditCardFactory.class, payment);
        assertEquals(1200.00, payment.getAmount());
        assertEquals("CUST001", payment.getCustomerId());
        assertEquals("CREDIT_CARD", payment.getPaymentMethod());
    }
    
    @Test
    @DisplayName("PaypalPaymentFactory should create PaypalFactory instance")
    void testPaypalFactoryCreation() {
        PaymentFactory factory = new PaypalPaymentFactory(
            "user@example.com",
            "AUTH_TOKEN_12345"
        );
        
        PaymentMethod payment = factory.createPaymentMethod(800.00, "CUST002", "Payment");
        
        assertNotNull(payment);
        assertInstanceOf(PaypalFactory.class, payment);
        assertEquals(800.00, payment.getAmount());
        assertEquals("CUST002", payment.getCustomerId());
        assertEquals("PAYPAL", payment.getPaymentMethod());
    }
    
    @Test
    @DisplayName("CryptoPaymentFactory should create CryptoFactory instance")
    void testCryptoFactoryCreation() {
        PaymentFactory factory = new CryptoPaymentFactory(
            "1A1z7agoat2Bt89zrzNrCiCWxMChSqP824",
            "BITCOIN",
            2.5
        );
        
        PaymentMethod payment = factory.createPaymentMethod(45.99, "CUST003", "Payment");
        
        assertNotNull(payment);
        assertInstanceOf(CryptoFactory.class, payment);
        assertEquals(45.99, payment.getAmount());
        assertEquals("CUST003", payment.getCustomerId());
        assertEquals("CRYPTOCURRENCY", payment.getPaymentMethod());
    }
    
    @Test
    @DisplayName("Factory should generate different transaction IDs for each payment")
    void testUniqueTransactionIds() {
        PaymentFactory factory = new CreditCardPaymentFactory(
            "4532123456789012",
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10"
        );
        
        PaymentMethod payment1 = factory.createPaymentMethod(100.00, "CUST001", "Payment1");
        PaymentMethod payment2 = factory.createPaymentMethod(100.00, "CUST001", "Payment2");
        
        assertNotEquals(payment1.getTransactionId(), payment2.getTransactionId(),
            "Each payment should have a unique transaction ID");
    }
}
