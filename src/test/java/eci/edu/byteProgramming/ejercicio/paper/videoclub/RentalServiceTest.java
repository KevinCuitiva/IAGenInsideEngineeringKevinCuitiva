package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RentalServiceTest {

    @Test
    void premiumReceiptCalculatesExpectedDiscountAndTotal() {
        RentalService rentalService = RentalService.withDefaultCatalog();

        RentalReceipt receipt = rentalService.createReceipt(MembershipType.PREMIUM, "1,3");

        assertEquals(13000, receipt.getSubtotal());
        assertEquals(2600, receipt.getDiscount());
        assertEquals(10400, receipt.getTotal());
        assertEquals(2, receipt.getMovies().size());
        assertEquals("Interestellar", receipt.getMovies().get(0).getTitle());
        assertEquals("Inception", receipt.getMovies().get(1).getTitle());
    }

    @Test
    void unavailableMoviesAreIgnored() {
        RentalService rentalService = RentalService.withDefaultCatalog();

        RentalReceipt receipt = rentalService.createReceipt(MembershipType.BASICA, "2,4");

        assertEquals(1, receipt.getMovies().size());
        assertEquals("Matrix", receipt.getMovies().get(0).getTitle());
        assertEquals(6000, receipt.getSubtotal());
        assertEquals(0, receipt.getDiscount());
        assertEquals(6000, receipt.getTotal());
    }

    @Test
    void receiptRenderingMatchesExpectedStructure() {
        RentalReceipt receipt = new RentalReceipt(
                MembershipType.PREMIUM,
                List.of(
                        new Movie("Interestellar", MovieFormat.PHYSICAL, 8000, true),
                        new Movie("Inception", MovieFormat.DIGITAL, 5000, true)
                ),
                13000,
                2600,
                10400
        );

        String rendered = receipt.render();

        assertTrue(rendered.contains("--- RECIBO DE ALQUILER ---"));
        assertTrue(rendered.contains("Cliente: Premium"));
        assertTrue(rendered.contains(" - Interestellar (Fisica) - $8.000"));
        assertTrue(rendered.contains("Subtotal: $13.000"));
        assertTrue(rendered.contains("Descuento (20%): $2.600"));
        assertTrue(rendered.contains("Total a pagar: $10.400"));
    }
}