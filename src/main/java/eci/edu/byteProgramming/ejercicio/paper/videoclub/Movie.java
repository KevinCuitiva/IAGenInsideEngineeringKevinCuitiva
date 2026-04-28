package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public class Movie {
    private final String title;
    private final MovieFormat format;
    private final int price;
    private final boolean available;

    public Movie(String title, MovieFormat format, int price, boolean available) {
        this.title = title;
        this.format = format;
        this.price = price;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public MovieFormat getFormat() {
        return format;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getAvailabilityLabel() {
        return available ? "Disponible" : "No disponible";
    }
}