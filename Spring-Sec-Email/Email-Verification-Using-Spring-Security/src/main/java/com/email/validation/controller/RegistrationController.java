package com.email.validation.controller;



import com.email.validation.entity.UserAuthentication;
import com.email.validation.entity.VerificationToken;
import com.email.validation.repo.VerificationTokenRepository;
import com.email.validation.service.EmailService;
import com.email.validation.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private UserAuthenticationService userAuthService;
    @Autowired
    VerificationTokenRepository   verificationTokenRepository;
    @Autowired
    EmailService emailService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserAuthentication());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserAuthentication user) {
        // Register the user
        userAuthService.registerUser(user);
        System.out.println("Register controller " + user.toString());

        // Create and save verification token
        String token = UUID.randomUUID().toString(); // Generate a unique token
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

        // Send verification email with the token link
       // String verificationUrl = "http://yourapp.com/api/auth/verify?token=" + token;

        String verificationUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("/api/auth/verify")
                .queryParam("token", token)
                .toUriString();
        emailService.sendVerificationEmail(user.getEmail(), verificationUrl);

        // Redirect to login with registration success message
        return "redirect:/login?registered=true";
    }


    @GetMapping("/home")
    public String home() {
        return "home"; // Returns the name of the home.html view
    }

    @GetMapping("/login")
    public String showLoginForm() {

        return "login";
    }

    @GetMapping("/login/error")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "message", required = false) String message,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", message);
        }
        return "login";
    }


    @GetMapping("/logout")
    public String showlogOutm() {

        return "logout";
    }
}