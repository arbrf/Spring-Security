package com.email.validation;



import com.email.validation.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendVerificationEmail_shouldSendEmail() {
        String email = "sk.mohammed5811@gmail.com";
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=55cbd873-ef1a-4853-8eca-caddb11547bd";

        emailService.sendVerificationEmail(email, verificationUrl);

        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

