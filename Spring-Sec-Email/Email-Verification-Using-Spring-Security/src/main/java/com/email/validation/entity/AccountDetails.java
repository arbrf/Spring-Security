package com.email.validation.entity;

public class AccountDetails {

    private String name;
    private String email;

    // Constructor
    public AccountDetails(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

