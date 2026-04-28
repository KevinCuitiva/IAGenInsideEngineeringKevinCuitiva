package eci.edu.byteProgramming.ejercicio.paper.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test suite para VirtualStore
 * Valida el funcionamiento del Facade pattern y la orquestación del sistema de tienda virtual.
 */
@DisplayName("Virtual Store - Facade Pattern Tests")
class VirtualStoreTest {
    
    private VirtualStore store;
    
    @BeforeEach
    void setUp() {
        store = new VirtualStore();
    }
    
    @Test
    @DisplayName("Should create VirtualStore successfully")
    void testVirtualStoreCreation() {
        assertNotNull(store.getInventory());
        assertNotNull(store.getECIPayment());
    }
    
    @Test
    @DisplayName("Should retrieve product from inventory")
    void testGetProduct() {
        Product product = store.getProduct("LAPTOP001");
        assertNotNull(product);
        assertEquals("Gaming Laptop", product.getName());
        assertEquals(1200.00, product.getPrice());
        assertEquals("Electronics", product.getCategory());
    }
    
    @Test
    @DisplayName("Should return correct stock for product")
    void testGetStock() {
        int stock = store.getStock("LAPTOP001");
        assertTrue(stock > 0);
        assertEquals(5, stock);
    }
    
    @Test
    @DisplayName("Should return zero stock for non-existent product")
    void testGetStockNonExistent() {
        int stock = store.getStock("NONEXISTENT");
        assertEquals(0, stock);
    }
    
    @Test
    @DisplayName("Should process successful credit card payment")
    void testSuccessfulCreditCardPayment() {
        PaymentFactory creditCardFactory = new CreditCardPaymentFactory(
            "4532123456789012",
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10 #20-30, Bogotá"
        );
        
        boolean result = store.processPurchase(
            creditCardFactory,
            1200.00,
            "CUST001",
            "Gaming Laptop",
            "Juan Pérez",
            "juan@example.com",
            "LAPTOP001"
        );
        
        assertTrue(result, "Payment should be successful with valid credit card");
        
        // Verify stock was decremented
        int newStock = store.getStock("LAPTOP001");
        assertEquals(4, newStock, "Stock should be decremented after successful payment");
    }
    
    @Test
    @DisplayName("Should process successful PayPal payment")
    void testSuccessfulPayPalPayment() {
        PaymentFactory paypalFactory = new PaypalPaymentFactory(
            "maria@example.com",
            "AUTH_TOKEN_ABC123DEF456"
        );
        
        boolean result = store.processPurchase(
            paypalFactory,
            800.00,
            "CUST002",
            "Premium Smartphone",
            "María García",
            "maria@example.com",
            "PHONE001"
        );
        
        assertTrue(result, "Payment should be successful with valid PayPal");
        
        // Verify stock was decremented
        int newStock = store.getStock("PHONE001");
        assertEquals(9, newStock, "Stock should be decremented after successful payment");
    }
    
    @Test
    @DisplayName("Should process successful Cryptocurrency payment")
    void testSuccessfulCryptoPayment() {
        PaymentFactory cryptoFactory = new CryptoPaymentFactory(
            "1A1z7agoat2Bt89zrzNrCiCWxMChSqP824",
            "BITCOIN",
            100.0  // Balance suficiente
        );
        
        boolean result = store.processPurchase(
            cryptoFactory,
            45.99,
            "CUST003",
            "Java Programming Book",
            "Carlos López",
            "carlos@example.com",
            "BOOK001"
        );
        
        assertTrue(result, "Payment should be successful with valid cryptocurrency");
        
        // Verify stock was decremented
        int newStock = store.getStock("BOOK001");
        assertEquals(19, newStock, "Stock should be decremented after successful payment");
    }
    
    @Test
    @DisplayName("Should fail payment for non-existent product")
    void testPaymentForNonExistentProduct() {
        PaymentFactory factory = new CreditCardPaymentFactory(
            "4532123456789012",
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10"
        );
        
        boolean result = store.processPurchase(
            factory,
            100.00,
            "CUST001",
            "Unknown Product",
            "Juan Pérez",
            "juan@example.com",
            "INVALID_ID"
        );
        
        assertFalse(result, "Payment should fail for non-existent product");
    }
    
    @Test
    @DisplayName("Should fail payment with invalid credit card")
    void testPaymentWithInvalidCreditCard() {
        PaymentFactory factory = new CreditCardPaymentFactory(
            "123",  // Too short
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10"
        );
        
        boolean result = store.processPurchase(
            factory,
            1200.00,
            "CUST001",
            "Gaming Laptop",
            "Juan Pérez",
            "juan@example.com",
            "LAPTOP001"
        );
        
        assertFalse(result, "Payment should fail with invalid credit card number");
    }
    
    @Test
    @DisplayName("Should fail payment with invalid PayPal email")
    void testPaymentWithInvalidPayPalEmail() {
        PaymentFactory factory = new PaypalPaymentFactory(
            "invalid_email",  // No @ or .
            "AUTH_TOKEN_ABC123DEF456"
        );
        
        boolean result = store.processPurchase(
            factory,
            800.00,
            "CUST002",
            "Smartphone",
            "María García",
            "maria@example.com",
            "PHONE001"
        );
        
        assertFalse(result, "Payment should fail with invalid PayPal email");
    }
    
    @Test
    @DisplayName("Should fail crypto payment with insufficient balance")
    void testCryptoPaymentWithInsufficientBalance() {
        PaymentFactory factory = new CryptoPaymentFactory(
            "1A1z7agoat2Bt89zrzNrCiCWxMChSqP824",
            "BITCOIN",
            0.01  // Too little balance
        );
        
        boolean result = store.processPurchase(
            factory,
            45.99,
            "CUST003",
            "Java Programming Book",
            "Carlos López",
            "carlos@example.com",
            "BOOK001"
        );
        
        assertFalse(result, "Payment should fail with insufficient crypto balance");
    }
}
