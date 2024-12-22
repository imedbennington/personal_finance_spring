package tn.iteam.springpersofinance.interfaces;

import tn.iteam.springpersofinance.entities.Budget;
import tn.iteam.springpersofinance.entities.Category;

import java.util.List;

public interface BudgetInterface {
        void addBudget(Budget budget, String categoryName, Long categoryId);  // Corrected parameter name
        void updateBudget(Budget budget, Long id);
        void deleteBudget(Budget budget, Long id);
        List<Budget> getAllBudgets();
        Budget getBudgetById(Long id);
        List<Budget> getBudgetByName(String budgetName);

}
