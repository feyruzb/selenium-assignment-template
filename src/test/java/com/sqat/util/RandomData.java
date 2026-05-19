package com.sqat.util;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomData {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private RandomData() {}

    public static String firstName() {
        return capitalized(letters(6));
    }

    public static String lastName() {
        return capitalized(letters(8));
    }

    public static String email() {
        return "sqat_" + System.currentTimeMillis() + "_" + letters(5).toLowerCase() + "@example.com";
    }

    public static String password() {
        return capitalized(letters(5)) + digits(3) + "!";
    }

    public static String phrase() {
        return capitalized(letters(5)) + " " + letters(6).toLowerCase();
    }

    private static String letters(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(LOWER.charAt(ThreadLocalRandom.current().nextInt(LOWER.length())));
        }
        return sb.toString();
    }

    private static String digits(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(DIGITS.charAt(ThreadLocalRandom.current().nextInt(DIGITS.length())));
        }
        return sb.toString();
    }

    private static String capitalized(String s) {
        if (s.isEmpty()) return s;
        return UPPER.charAt(ThreadLocalRandom.current().nextInt(UPPER.length())) + s.substring(1);
    }
}
