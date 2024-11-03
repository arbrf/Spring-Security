package com.email.validation.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendVerificationEmail(String email, String verificationUrl) {
        // Logic to send an email with verificationUrl
        System.out.println("Verification email sent to " + email + " with URL: " + verificationUrl);
    }
}

