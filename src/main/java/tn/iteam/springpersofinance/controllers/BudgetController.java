package tn.iteam.springpersofinance.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
    public BudgetController(CategoryService categoryService, BudgetService budgetService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    @GetMapping("/budgets")
    public String getAllBudgets(@RequestParam(required = false) Long userId, Model model) {
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        model.addAttribute("user", user);
        model.addAttribute("budget", new Budget());
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("content", "budgets/get_budgets");
        return "layouts/layouts";
    }




    @GetMapping("/get-add-budgets")
    public String get_add_budgets(HttpSession session, Model model) {
        String loggedInUserName = (String) session.getAttribute("loggedInUserName");
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        // Add the user info to the model
        model.addAttribute("loggedInUserName", loggedInUserName);
        model.addAttribute("loggedInUserId", loggedInUserId);

        // Fetch user details from the database
        User user = userRepository.findById(loggedInUserId).orElse(null);
        model.addAttribute("user", user);

        // Add other attributes
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


@PostMapping("/delete-budget/{id}")
    public String deleteBudget(@RequestParam Long budgetId, Model model) {
        Budget budget = budgetService.getBudgetById(budgetId);
        User user = null;
        budgetService.deleteBudget(budget, budgetId);
        model.addAttribute("user", user);
        model.addAttribute("budget", new Budget());
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("content", "budgets/get_budgets");
    return "layouts/layouts";
}

    @GetMapping("/budgets/edit/{id}")
    public String editBudget(@PathVariable Long id, Model model) {
        Budget budget = budgetService.getBudgetById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("budget", budget);
        model.addAttribute("content", "budgets/update-budget");
        return "layouts/layouts"; // Main layout for the application
    }

/*    @PostMapping("/budgets/update/{id}")
    public String updateBudget(@PathVariable Long id, @Valid @ModelAttribute Budget budget, Model model) {
        budgetService.updateBudget(budget, id);
        User user = null;
        model.addAttribute("message", "Budget updated successfully!");
        model.addAttribute("user", user);
        model.addAttribute("budget", new Budget());
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("content", "budgets/get_budgets");
        return "layouts/layouts";
    }*/

    @PostMapping("/budgets/update/{id}")
    public String updateBudget(
            @PathVariable Long id,
            @Valid @ModelAttribute Budget budget,
            Category category,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("budget", budget);
            model.addAttribute("category", category);
            model.addAttribute("categories", categoryService.getAllCategories()); // Populate categories for dropdown
            model.addAttribute("message", "Please correct the errors in the form.");
            model.addAttribute("content", "budgets/edit_budget");
            return "layouts/layouts";
        }

        try {
            budgetService.updateBudget(budget, id);
            model.addAttribute("message", "Budget updated successfully!");
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("content", "budgets/get_budgets");
        return "layouts/layouts";
    }

}


