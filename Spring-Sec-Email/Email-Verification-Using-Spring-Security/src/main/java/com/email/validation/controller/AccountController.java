package com.email.validation.controller;

import com.email.validation.entity.AccountDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/accounts")
    public String showAccountPage(Model model) {
        // Add attributes to the model, for example:
        model.addAttribute("accountDetails", getAccountDetails());
        return "account";  // This corresponds to account.html
    }

    private AccountDetails getAccountDetails() {
        // Logic to fetch account details, can be from a service or database
        return new AccountDetails("Sample Account", "user@example.com");
    }
}
