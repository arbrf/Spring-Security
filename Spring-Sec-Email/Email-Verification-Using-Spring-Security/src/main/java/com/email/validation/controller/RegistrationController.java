package com.email.validation.controller;



import com.email.validation.entity.UserAuthentication;
import com.email.validation.entity.VerificationToken;
import com.email.validation.repo.UserAuthenticationRepository;
import com.email.validation.repo.VerificationTokenRepository;
import com.email.validation.service.CookieExample;
import com.email.validation.service.EmailService;
import com.email.validation.service.UserAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Controller
public class RegistrationController {

    @Autowired
    private BCryptPasswordEncoder  passwordEncoder;

    @Autowired
    private UserAuthenticationService userAuthService;
    @Autowired
    VerificationTokenRepository   verificationTokenRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    private UserAuthenticationRepository userRepo;

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
        CookieExample.createCooke();
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


    @PostMapping("/logout")
    public String handleLogoutPost() {
        // This method handles POST requests to "/logout"
        // Redirects to the logout confirmation page after logout
        System.out.println("POSTMAPPNG LOGOUT ");
        return "redirect:/logout-page";
    }

    @GetMapping("/logout-page")
    public String showLogoutPage() {
        // This method handles GET requests to "/logout-page"
        // Displays the custom logout page (e.g., logout.html)
        return "logout";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgotpassword";
    }
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<UserAuthentication> user=userRepo.findByEmail(email);
        if (user == null) {
            // If no user found, show error message
            model.addAttribute("error", "No account found with that email.");
            return "forgotpassword";  // Return to the forgot-password page with an error
        }

        String resetPasswordUrl = "http://localhost:8080/reset-password?email=" + email;
        emailService.forgotPassword(email, resetPasswordUrl);

        model.addAttribute("message", "A password reset link has been sent to your email.");
        return "login";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("email") String email, Model model) {
        // You can check if the email exists in the database or any other logic before showing the form
        model.addAttribute("email", email);
        return "ResetPassword"; // Return the view name for password reset
    }

    // Handle the password reset request when the form is submitted
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {

        // Check if the passwords match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "resetpassword"; // Stay on the reset password page with an error message
        }
      System.out.println("Inside Post Mapping of resetPassword");
        // Retrieve the user from the database using the email
        Optional<UserAuthentication> userOptional=userRepo.findByEmail(email);
        UserAuthentication user = userOptional.get();
        if (user == null) {
            model.addAttribute("error", "User not found with email: " + email);
            return "resetpassword"; // Stay on the reset password page with an error message
        }



        // Encrypt the new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Set the new password
        user.setPassword(encodedPassword);

        // Save the updated user details
        userRepo.save(user);

        // Redirect to the login page with a success message
        return "redirect:/login?passwordReset=true";
    }



    @GetMapping("/invalidate")
    public String invalidateSession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?invalidated=true";
    }



}