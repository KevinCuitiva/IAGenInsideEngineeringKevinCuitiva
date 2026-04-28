package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.List;
import java.util.Locale;

public class RentalReceipt {
    private final MembershipType membershipType;
    private final List<Movie> movies;
    private final int subtotal;
    private final int discount;
    private final int total;

    public RentalReceipt(MembershipType membershipType, List<Movie> movies, int subtotal, int discount, int total) {
        this.membershipType = membershipType;
        this.movies = List.copyOf(movies);
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public int getDiscount() {
        return discount;
    }

    public int getTotal() {
        return total;
    }

    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append("--- RECIBO DE ALQUILER ---").append(System.lineSeparator());
        builder.append("Cliente: ").append(membershipType.getDisplayName()).append(System.lineSeparator());
        builder.append("Peliculas:").append(System.lineSeparator());

        for (Movie movie : movies) {
            builder.append(" - ")
                    .append(movie.getTitle())
                    .append(" (")
                    .append(movie.getFormat().getDisplayName())
                    .append(") - $")
                    .append(formatMoney(movie.getPrice()))
                    .append(System.lineSeparator());
        }

        builder.append("Subtotal: $").append(formatMoney(subtotal)).append(System.lineSeparator());
        builder.append("Descuento (").append(getDiscountLabel()).append("): $")
                .append(formatMoney(discount)).append(System.lineSeparator());
        builder.append("Total a pagar: $").append(formatMoney(total)).append(System.lineSeparator());
        builder.append("--------------------------").append(System.lineSeparator());
        builder.append("¡Disfrute su pelicula!");
        return builder.toString();
    }

    private String getDiscountLabel() {
        return membershipType == MembershipType.PREMIUM ? "20%" : "0%";
    }

    private String formatMoney(int amount) {
        String formatted = String.format(Locale.US, "%,d", amount);
        return formatted.replace(',', '.');
    }
}