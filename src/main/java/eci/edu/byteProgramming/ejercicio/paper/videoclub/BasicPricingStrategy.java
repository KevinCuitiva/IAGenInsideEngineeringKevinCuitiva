package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public class BasicPricingStrategy implements PricingStrategy {
    @Override
    public int applyDiscount(int subtotal) {
        return 0;
    }

    @Override
    public String getDiscountLabel() {
        return "0%";
    }
}