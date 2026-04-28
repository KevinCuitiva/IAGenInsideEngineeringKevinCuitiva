package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Concrete Factory for Cryptocurrency Payments
 * Implementa el patrón Factory para crear instancias de CryptoFactory
 */
public class CryptoPaymentFactory implements PaymentFactory {
    private String walletAddress;
    private String cryptoType;
    private double walletBalance;
    
    public CryptoPaymentFactory(String walletAddress, String cryptoType, double walletBalance) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
        this.walletBalance = walletBalance;
    }
    
    @Override
    public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
        return new CryptoFactory(amount, customerId, description, walletAddress, cryptoType, walletBalance);
    }
}
