package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.Scanner;

public class VideoclubConsoleApp {
    public static void main(String[] args) {
        RentalService rentalService = RentalService.withDefaultCatalog();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(rentalService.renderCatalog());
            System.out.print("Membresia del cliente (Basica/Premium): ");
            MembershipType membershipType = MembershipType.fromInput(scanner.nextLine());
            System.out.print("Seleccione peliculas (numeros separados por coma): ");
            String selectionInput = scanner.nextLine();

            RentalReceipt receipt = rentalService.createReceipt(membershipType, selectionInput);
            System.out.println();
            System.out.println(receipt.render());
        }
    }
}