package com.spring_security_pwd_validation.demo.controller;

import com.spring_security_pwd_validation.demo.dao.UserDaoService;
import com.spring_security_pwd_validation.demo.dto.UserData;
import com.spring_security_pwd_validation.demo.repo.UserServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserDaoService userDaoService;
    @Autowired
    private UserServiceRepo userServiceRepo;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userData", new UserData());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userData") UserData userData) {
        // Handle user registration logic (e.g., save to database)
        System.out.println("Registered User: " + userData.getFirstName() + " " + userData.getLastName());
        userDaoService.printUSerDatatoConsole(userData);
        userServiceRepo.saveUserData(userData);
        return "redirect:/registration-success"; // Redirect after successful registration
    }

    @GetMapping("/registration-success")
    public String registrationSuccess() {
        return "registration-success"; // Returns the registration success view
    }

}

