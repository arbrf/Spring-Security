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
        message.setFrom("sk.mohammed5811@gmail.com");
        message.setTo(email);
        message.setSubject("Congrats Your Profile Shortlisted");
        message.setText("We are delight to offer u this position this your token  "+verificationUrl); // Set the email body
        emailSender.send(message); // Send the email
        System.out.println("Mail Sent Successfully!");

        System.out.println("Verification email sent to " + email + " with URL: " + verificationUrl);
    }


    public void forgotPassword(String email, String resetPasswordUtl) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sk.mohammed5811@gmail.com");
        message.setTo(email);
        message.setSubject("Congrats Your Profile Shortlisted");
        message.setText("Please Change this password using this link  "+resetPasswordUtl); // Set the email body
        emailSender.send(message); // Send the email
        System.out.println("Mail Sent Successfully!");

        System.out.println("Verification email sent to " + email + " with URL: " + resetPasswordUtl);
    }




}


