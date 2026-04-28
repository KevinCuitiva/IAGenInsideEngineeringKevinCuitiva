package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public interface PricingStrategy {
    int applyDiscount(int subtotal);

    String getDiscountLabel();
}