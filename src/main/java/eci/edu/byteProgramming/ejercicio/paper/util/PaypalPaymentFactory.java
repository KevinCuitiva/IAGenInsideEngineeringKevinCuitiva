package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Concrete Factory for PayPal Payments
 * Implementa el patrón Factory para crear instancias de PaypalFactory
 */
public class PaypalPaymentFactory implements PaymentFactory {
    private String email;
    private String authToken;
    
    public PaypalPaymentFactory(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
    }
    
    @Override
    public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
        return new PaypalFactory(amount, customerId, description, email, authToken);
    }
}
