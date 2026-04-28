package eci.edu.byteProgramming.ejercicio.paper.util;

import java.util.Scanner;

/**
 * Virtual Store Console Application
 * Demuestra el uso del sistema de tienda virtual con soporte para múltiples métodos de pago.
 */
public class VirtualStoreConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final VirtualStore store = new VirtualStore();
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       🛒 Welcome to ECI Virtual Store                       ║");
        System.out.println("║     Sistema de Pagos con Múltiples Métodos                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        store.listAvailableProducts();
        
        // Demo: Venta con tarjeta de crédito
        demoCreditCardPayment();
        
        // Demo: Venta con PayPal
        demoPayPalPayment();
        
        // Demo: Venta con Criptomoneda
        demoCryptoPayment();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              Thank you for using ECI Virtual Store!        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        scanner.close();
    }
    
    /**
     * Demuestra una compra con tarjeta de crédito
     */
    private static void demoCreditCardPayment() {
        System.out.println("\n\n💳 DEMO 1: Credit Card Payment");
        System.out.println("════════════════════════════════════════════════════════");
        
        // Crear factory para tarjeta de crédito
        PaymentFactory creditCardFactory = new CreditCardPaymentFactory(
            "4532123456789012",  // Visa
            "Juan Pérez",
            "12/25",
            "123",
            "Calle 10 #20-30, Bogotá"
        );
        
        // Procesar compra
        boolean success = store.processPurchase(
            creditCardFactory,
            1200.00,
            "CUST001",
            "Gaming Laptop",
            "Juan Pérez",
            "juan@example.com",
            "LAPTOP001"
        );
        
        System.out.println("Transaction Status: " + (success ? "✅ SUCCESS" : "❌ FAILED"));
    }
    
    /**
     * Demuestra una compra con PayPal
     */
    private static void demoPayPalPayment() {
        System.out.println("\n\n🌐 DEMO 2: PayPal Payment");
        System.out.println("════════════════════════════════════════════════════════");
        
        // Crear factory para PayPal
        PaymentFactory paypalFactory = new PaypalPaymentFactory(
            "maria@example.com",
            "AUTH_TOKEN_ABC123DEF456"
        );
        
        // Procesar compra
        boolean success = store.processPurchase(
            paypalFactory,
            800.00,
            "CUST002",
            "Premium Smartphone",
            "María García",
            "maria@example.com",
            "PHONE001"
        );
        
        System.out.println("Transaction Status: " + (success ? "✅ SUCCESS" : "❌ FAILED"));
    }
    
    /**
     * Demuestra una compra con criptomoneda
     */
    private static void demoCryptoPayment() {
        System.out.println("\n\n₿ DEMO 3: Cryptocurrency Payment");
        System.out.println("════════════════════════════════════════════════════════");
        
        // Crear factory para criptomoneda
        PaymentFactory cryptoFactory = new CryptoPaymentFactory(
            "1A1z7agoat2Bt89zrzNrCiCWxMChSqP824",  // Bitcoin address (válido)
            "BITCOIN",
            2.5
        );
        
        // Procesar compra
        boolean success = store.processPurchase(
            cryptoFactory,
            45.99,
            "CUST003",
            "Java Programming Book",
            "Carlos López",
            "carlos@example.com",
            "BOOK001"
        );
        
        System.out.println("Transaction Status: " + (success ? "✅ SUCCESS" : "❌ FAILED"));
    }
}
