package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.List;

public final class MovieFactory {
    private MovieFactory() {
    }

    public static Movie createMovie(String title, MovieFormat format, int price, boolean available) {
        return new Movie(title, format, price, available);
    }

    public static List<Movie> createDefaultCatalog() {
        return List.of(
                createMovie("Interestellar", MovieFormat.PHYSICAL, 8000, true),
                createMovie("El Padrino", MovieFormat.PHYSICAL, 7000, false),
                createMovie("Inception", MovieFormat.DIGITAL, 5000, true),
                createMovie("Matrix", MovieFormat.DIGITAL, 6000, true)
        );
    }
}