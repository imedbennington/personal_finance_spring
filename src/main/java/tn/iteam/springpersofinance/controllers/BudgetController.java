package tn.iteam.springpersofinance.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.iteam.springpersofinance.entities.Budget;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.repositories.UserRepository;
import tn.iteam.springpersofinance.service.BudgetService;
import tn.iteam.springpersofinance.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
    public BudgetController(CategoryService categoryService, BudgetService budgetService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    @GetMapping("/budgets")
    public String getAllBudgets(Model model) {
        model.addAttribute("budget", new Budget());
        model.addAttribute("budgets", budgetService.getAllBudgets()); // Make sure you have this method in your service
        model.addAttribute("content", "budgets/get_budgets");
        return "layouts/layouts";
    }

    @GetMapping("/get-add-budgets")
    public String get_add_budgets(Model model ) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("budget", new Budget());
        model.addAttribute("content", "budgets/add_budgets");
        return "layouts/layouts";
    }
@GetMapping("/error")
public String error(Model model) {
    model.addAttribute("content", "errors/error");
    return "layouts/layouts";
}
    @PostMapping("/budgets-add")
    /*public String post_budget(Model model, @ModelAttribute("budget") Budget budget, String category) {
        budgetService.addBudget(budget, category);
        return "redirect:/budgets";
    }*/

    public String postBudget(@RequestParam Long categoryId, @RequestParam Long userId, @RequestParam String categoryName, @ModelAttribute Budget budget, Model model) {
        try {
            // Call the service method to add the budget
            // Fetch the User from the database using the userId
            // Fetch the User from the database using the userId
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));

            // Set the User in the Budget
            budget.setUser(user);

            // Set the User in the Budget
            budget.setUser(user);
            budgetService.addBudget(budget,categoryName, categoryId);
            return "redirect:/budgets";  // Redirect to a success page after adding the budget
        } catch (RuntimeException e) {
            // Capture the stack trace and convert it to a string
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            // Log the stack trace for developers
            logger.error("Error adding budget", e);

            // Add the error message and the stack trace as attributes to the model
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("stackTrace", sw.toString());

            return "redirect:/error";  // Redirect to the error page
        }
    }
}
