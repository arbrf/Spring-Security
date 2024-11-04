package com.email.validation.service;

import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationEmail(String email, String verificationUrl) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sk.davoodh5811@email.com");
        message.setTo("sk.mohammed5811@gmail.com");
        message.setSubject("Email Verification");
        message.setText("Please verify your email by clicking on the following link: " + verificationUrl);

        emailSender.send(message);
        System.out.println("Verification email sent to " + email + " with URL: " + verificationUrl);
    }

}


