package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Concrete Factory for Credit Card Payments
 * Implementa el patrón Factory para crear instancias de CreditCardFactory
 */
public class CreditCardPaymentFactory implements PaymentFactory {
    private String number;
    private String name;
    private String expirationDate;
    private String cvv;
    private String address;
    
    public CreditCardPaymentFactory(String number, String name, String expirationDate, String cvv, String address) {
        this.number = number;
        this.name = name;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.address = address;
    }
    
    @Override
    public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
        return new CreditCardFactory(amount, customerId, description, number, name, expirationDate, cvv, address);
    }
}
