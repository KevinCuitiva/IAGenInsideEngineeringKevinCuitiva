package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public class PremiumPricingStrategy implements PricingStrategy {
    private static final double DISCOUNT_RATE = 0.20;

    @Override
    public int applyDiscount(int subtotal) {
        return (int) Math.round(subtotal * DISCOUNT_RATE);
    }

    @Override
    public String getDiscountLabel() {
        return "20%";
    }
}