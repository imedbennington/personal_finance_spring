package tn.iteam.springpersofinance.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.iteam.springpersofinance.entities.*;
import tn.iteam.springpersofinance.exceptions.TransEception;
import tn.iteam.springpersofinance.repositories.UserRepository;
import tn.iteam.springpersofinance.service.AccountsService;
import tn.iteam.springpersofinance.service.CategoryService;
import tn.iteam.springpersofinance.service.TransactionService;
import tn.iteam.springpersofinance.service.UserService;

@Controller
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountsService accountsService;
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    public TransactionController(TransactionService transactionService, CategoryService categoryService, AccountsService accountsService, UserService userService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.accountsService = accountsService;
        this.userService = userService;
    }

    @GetMapping("/get-add-trans")
    public String getAddTransaction(Model model) {
        model.addAttribute("accounts", new Account());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accounts", accountsService.getAccounts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "transactions/add_transaction2");
        return "layouts/layouts";
    }

    @GetMapping("/get-transactions")
    public String getTransactions(Model model) {
        model.addAttribute("accounts", new Account());
        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("content", "transactions/get-transactions");
        return "layouts/layouts";
    }
    @PostMapping("/transactions/add")
    public String addTransaction(
            @ModelAttribute("transaction") Transaction transaction,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("sourceAccountId") Long sourceAccountId,
            @RequestParam("targetAccountId") Long targetAccountId,
            @RequestParam("userId") Long userId,
            Model model) {
        try {

            User loggedInUser = userRepository.findById(userId).get();
            transaction.setUser(loggedInUser);
            // Add required attributes to the model
            model.addAttribute("accounts", accountsService.getAccounts());
            model.addAttribute("categories", categoryService.getAllCategories());

            // Call the service to add the transaction
            transactionService.addTransaction(transaction, categoryName, categoryId, sourceAccountId, targetAccountId);
            model.addAttribute("content", "transactions/get-transactions");
            // Redirect or display success message
            return "redirect:/get-transactions"; // Redirect to the transaction list page
        } catch (TransEception e) {
            // Add error message to the model and reload the form
            model.addAttribute("error", e.getMessage());
            model.addAttribute("content", "transactions/get-transactions");
            return "layouts/layouts"; // Return the form view with error message
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    @GetMapping("/transaction/edit/{id}")
    public String getEditTransaction(Model model, @PathVariable("id") Long id) {
        Transaction transaction = transactionService.getTransaction(id);
        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("transaction", transaction);
        model.addAttribute("content", "transactions/edit-transaction");
        return "layouts/layouts";
    }

    @PostMapping("/delete-transaction/{id}")
    public String deleteTransaction(
            @PathVariable Long id,
            Model model
    ){
        Transaction transaction = transactionService.getTransaction(id);
        User user = null;
        transactionService.deleteTransaction(transaction, id);
        model.addAttribute("user", user);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("content", "transactions/get_transactions");
        return "layouts/layouts";
    }
    @PostMapping("/transaction/update/{id}")
    public String updateTransaction(
            @PathVariable Long id,
            @Valid @ModelAttribute Transaction transaction,
            Category category,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("transaction", transaction);
            model.addAttribute("category", category);
            model.addAttribute("transactions", transactionService.getTransactions()); // Populate categories for dropdown
            model.addAttribute("message", "Please correct the errors in the form.");
            //model.addAttribute("content", "budgets/edit_budget");
            return "layouts/layouts";
        }

        try {
            transactionService.updateTransaction(transaction, id);
            model.addAttribute("message", "Budget updated successfully!");
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("content", "transactions/get_transactions");
        return "layouts/layouts";
    }
}
