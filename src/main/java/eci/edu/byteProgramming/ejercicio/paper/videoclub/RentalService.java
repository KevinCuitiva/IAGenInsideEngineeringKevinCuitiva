package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RentalService {
    private final List<Movie> catalog;

    public RentalService(List<Movie> catalog) {
        this.catalog = List.copyOf(catalog);
    }

    public static RentalService withDefaultCatalog() {
        return new RentalService(MovieFactory.createDefaultCatalog());
    }

    public RentalReceipt createReceipt(MembershipType membershipType, String selectionInput) {
        PricingStrategy pricingStrategy = createPricingStrategy(membershipType);
        List<Movie> selectedMovies = selectAvailableMovies(selectionInput);
        int subtotal = selectedMovies.stream().mapToInt(Movie::getPrice).sum();
        int discount = pricingStrategy.applyDiscount(subtotal);
        int total = subtotal - discount;

        return new RentalReceipt(membershipType, selectedMovies, subtotal, discount, total);
    }

    public List<Movie> getCatalog() {
        return catalog;
    }

    public String renderCatalog() {
        StringBuilder builder = new StringBuilder();
        builder.append("Peliculas disponibles").append(System.lineSeparator());

        for (int index = 0; index < catalog.size(); index++) {
            Movie movie = catalog.get(index);
            builder.append(index + 1)
                    .append(") [")
                    .append(movie.getFormat().getDisplayName())
                    .append("] ")
                    .append(movie.getTitle())
                    .append(" - $")
                    .append(formatMoney(movie.getPrice()))
                    .append(" - ")
                    .append(movie.getAvailabilityLabel())
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }

    private PricingStrategy createPricingStrategy(MembershipType membershipType) {
        if (membershipType == MembershipType.PREMIUM) {
            return new PremiumPricingStrategy();
        }
        return new BasicPricingStrategy();
    }

    private List<Movie> selectAvailableMovies(String selectionInput) {
        Set<Integer> uniqueSelections = new LinkedHashSet<>();
        if (selectionInput != null && !selectionInput.isBlank()) {
            String[] tokens = selectionInput.split(",");
            for (String token : tokens) {
                try {
                    uniqueSelections.add(Integer.parseInt(token.trim()));
                } catch (NumberFormatException ignored) {
                    // Se ignoran entradas no numericas para mantener la experiencia de consola simple.
                }
            }
        }

        List<Movie> selectedMovies = new ArrayList<>();
        for (Integer selection : uniqueSelections) {
            int catalogIndex = selection - 1;
            if (catalogIndex >= 0 && catalogIndex < catalog.size()) {
                Movie movie = catalog.get(catalogIndex);
                if (movie.isAvailable()) {
                    selectedMovies.add(movie);
                }
            }
        }

        return selectedMovies;
    }

    private String formatMoney(int amount) {
        String formatted = String.format(java.util.Locale.US, "%,d", amount);
        return formatted.replace(',', '.');
    }
}