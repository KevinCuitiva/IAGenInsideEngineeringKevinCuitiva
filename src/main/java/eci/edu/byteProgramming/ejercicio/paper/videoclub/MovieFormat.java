package eci.edu.byteProgramming.ejercicio.paper.videoclub;

public enum MovieFormat {
    PHYSICAL("Fisica"),
    DIGITAL("Digital");

    private final String displayName;

    MovieFormat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}