package com.spring_security.Spring.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderDemo {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword1 = passwordEncoder.encode("hello");
        String encodedPassword2 = passwordEncoder.encode("dummy2");
        String encodedPassword3 = passwordEncoder.encode("dummy3");

        System.out.println("Encoded password for user1: " + encodedPassword1);
        System.out.println("Encoded password for user2: " + encodedPassword2);
        System.out.println("Encoded password for user3: " + encodedPassword3);
    }
}

