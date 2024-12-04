package com.email.validation.service;


import jakarta.servlet.http.Cookie;

public class CookieExample {
    public static void createCooke() {
        // Create a dummy cookie
        Cookie dummyCookie = new Cookie("dummyCookie", "dummyValue");

        // Set optional properties
        dummyCookie.setMaxAge(60 * 60); // Expires in 1 hour
        dummyCookie.setHttpOnly(true);  // Make it HttpOnly for security
        dummyCookie.setPath("/");       // Path for which the cookie is valid

        // Print cookie details
        System.out.println("Cookie Name: " + dummyCookie.getName());
        System.out.println("Cookie Value: " + dummyCookie.getValue());
    }
}

