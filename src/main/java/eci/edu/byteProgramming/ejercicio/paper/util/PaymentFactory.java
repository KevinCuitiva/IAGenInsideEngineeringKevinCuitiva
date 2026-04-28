package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Factory Pattern - Define el contrato para crear diferentes tipos de métodos de pago.
 * Este es un ejemplo de patrón creacional que abstrae la creación de objetos PaymentMethod.
 */
public interface PaymentFactory {
    
    /**
     * Crea un método de pago específico
     * @param amount Monto a pagar
     * @param customerId ID del cliente
     * @param description Descripción de la transacción
     * @return PaymentMethod concreto creado
     */
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
