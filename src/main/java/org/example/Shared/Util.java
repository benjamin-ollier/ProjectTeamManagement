package org.example.Shared;

public class Util {

    public static boolean isEmail(String identifier) {
        return identifier.contains("@") && identifier.contains(".");
    }
}