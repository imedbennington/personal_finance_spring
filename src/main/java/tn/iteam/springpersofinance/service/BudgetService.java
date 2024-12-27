package tn.iteam.springpersofinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.iteam.springpersofinance.entities.Budget;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.exceptions.BudgetNotFoundException;
import tn.iteam.springpersofinance.interfaces.BudgetInterface;
import tn.iteam.springpersofinance.repositories.BudgetRepository;
import tn.iteam.springpersofinance.repositories.UserRepository;

import java.util.List;
@Service
public class BudgetService implements BudgetInterface {

    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, CategoryService categoryService, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }


    @Override
    public void addBudget(Budget budget, String categoryName, Long categoryId) {
        try {
            // Fetch the category by its ID
            Category category = categoryService.getCategoryById(categoryId);
            // If the category is not found, throw an exception
            if (category == null) {
                throw new RuntimeException("Category not found for ID: " + categoryId);
            }

            // Set the category in the budget
            budget.setCategory(category);
            budget.setCategoryName(categoryName);  // Set the category name

            // Save the budget
            budgetRepository.save(budget);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add budget: " + e.getMessage(), e);
        }
    }


   /* @Override
    public void updateBudget(Budget budget, Long id) {
        try {
            Budget existingBudget = budgetRepository.findById(id)
                    .orElseThrow(() -> new BudgetNotFoundException("Budget with ID " + id + " not found."));
            existingBudget.setAmount(budget.getAmount());
            existingBudget.setDescription(budget.getDescription());
            existingBudget.setStartDate(budget.getStartDate());
            existingBudget.setEndDate(budget.getEndDate());
            existingBudget.setCategory(budget.getCategory());
            budgetRepository.save(existingBudget);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update budget: " + e.getMessage());
        }
    }*/

    @Override
    public void updateBudget(Budget budget, Long id) {
        try {
            Budget existingBudget = budgetRepository.findById(id)
                    .orElseThrow(() -> new BudgetNotFoundException("Budget with ID " + id + " not found."));

            existingBudget.setAmount(budget.getAmount());
            existingBudget.setDescription(budget.getDescription());
            existingBudget.setStartDate(budget.getStartDate());
            existingBudget.setEndDate(budget.getEndDate());

            // Fetch and set related category
            Category category = categoryService.getCategoryById(budget.getCategory().getId());
                    //.orElseThrow(() -> new RuntimeException("Category not found."));
            existingBudget.setCategory(category);

            // Fetch and set related user (if required)
            User user = userRepository.findById(budget.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found."));
            existingBudget.setUser(user);

            budgetRepository.save(existingBudget);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update budget: " + e.getMessage());
        }
    }


    @Override
    public void deleteBudget(Budget budget, Long id) {
        try {
            Budget existingBudget = budgetRepository.findById(id)
                    .orElseThrow(() -> new BudgetNotFoundException("Budget with ID " + id + " not found."));
            budgetRepository.delete(existingBudget);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete budget: " + e.getMessage());
        }
    }

    @Override
    public List<Budget> getAllBudgets() {
        try {
            return budgetRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all budgets: " + e.getMessage());
        }
    }

    @Override
    public Budget getBudgetById(Long id) {
        try {
            return budgetRepository.findById(id)
                    .orElseThrow(() -> new BudgetNotFoundException("Budget with ID " + id + " not found."));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch budget by ID: " + e.getMessage());
        }
    }

    @Override
    public List<Budget> getBudgetByName(String budgetName) {
        return List.of();
    }

    /*@Override
    public List<Budget> getBudgetByName(String budgetName) {
        try {
            return budgetRepository.findByDescriptionContainingIgnoreCase(budgetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch budgets by name: " + e.getMessage());
        }
    }*/


}
