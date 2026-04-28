package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.Locale;

public enum MembershipType {
    BASICA("Basica"),
    PREMIUM("Premium");

    private final String displayName;

    MembershipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MembershipType fromInput(String value) {
        if (value == null) {
            throw new IllegalArgumentException("La membresia es obligatoria");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);
        if (normalized.equals("BASICA") || normalized.equals("BASIC")) {
            return BASICA;
        }
        if (normalized.equals("PREMIUM")) {
            return PREMIUM;
        }

        throw new IllegalArgumentException("Membresia no valida: " + value);
    }
}