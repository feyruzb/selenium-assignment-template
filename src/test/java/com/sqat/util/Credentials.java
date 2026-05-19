package com.sqat.util;

public record Credentials(String firstName, String lastName, String email, String password) {

    public static Credentials random() {
        return new Credentials(
                RandomData.firstName(),
                RandomData.lastName(),
                RandomData.email(),
                RandomData.password()
        );
    }
}
