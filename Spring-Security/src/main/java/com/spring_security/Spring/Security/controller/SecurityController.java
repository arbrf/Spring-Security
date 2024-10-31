package com.spring_security.Spring.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // This refers to 'register.html' in 'src/main/resources/templates/'
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test successful";
    }

    @GetMapping("/home")
    public String showWelcomePage() {
        return "welcome"; // This refers to 'register.html' in 'src/main/resources/templates/'
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This matches login.html in templates
    }


  /*  @GetMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Here you would typically save the username and encoded password to the database.
        // For example: userRepository.save(new User(username, encodedPassword));

        // Adding a success message to the model
        model.addAttribute("message", "User registered successfully!");

        // Redirect to a success page or show the message on the same page
        return "registration-success"; // Create a corresponding HTML page to display success
    }*/


}
