package tn.iteam.springpersofinance.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.iteam.springpersofinance.entities.Account;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.repositories.AccountRepository;
import tn.iteam.springpersofinance.repositories.UserRepository;
import tn.iteam.springpersofinance.service.AccountsService;

@Controller
public class AccountController {
    @Autowired
    private final AccountsService accountsService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountController(AccountsService accountsService, AccountRepository accountRepository, UserRepository userRepository) {
        this.accountsService = accountsService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/get-create-account")
    public String getCreateAccount(HttpSession session, Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("content", "accounts_views/add_account");
        return "layouts/layouts";
    }

    @PostMapping("/create-account")
    public String createAccount(
            @RequestParam Long userId,
            @ModelAttribute("account") Account account,
            HttpSession session,
            Model model) {
        try {
            // Fetch the user by ID
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));

            // Associate the account with the user
            account.setUser(user);

            // Create the account using the service
            accountsService.createAccount(account);

            // Add attributes for the view
            model.addAttribute("account", account);
            model.addAttribute("content", "accounts_views/view_accounts");

        } catch (Exception e) {
            // Log the error and provide feedback
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while creating the account: " + e.getMessage());
            model.addAttribute("content", "accounts_views/add_account_error");
        }

        return "layouts/layouts";
    }


    @GetMapping("get-accounts")
    public String getAccounts(HttpSession session, Model model) {
        model.addAttribute("accounts", accountsService.getAccounts());
        model.addAttribute("content", "accounts_views/view_accounts");
        return "layouts/layouts";
    }


}