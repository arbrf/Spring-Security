package com.email.validation.controller;



import com.email.validation.entity.UserAuthentication;
import com.email.validation.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserAuthenticationService userAuthService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserAuthentication());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserAuthentication user) {
        userAuthService.registerUser(user);
        System.out.println("Resgister controlller "+user.toString());
        return "redirect:/login?registered=true";  // Redirect to login with a registration success message
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Returns the name of the home.html view
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        return "login";
    }
}