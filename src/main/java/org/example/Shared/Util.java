package org.example.Shared;

public class Util {
    public enum ExperienceCategory {
        JUNIOR,
        EXPERIENCED,
        EXPERT
    }

    public static boolean isEmail(String identifier) {
        return identifier.contains("@") && identifier.contains(".");
    }
}