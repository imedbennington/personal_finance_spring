package tn.iteam.springpersofinance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.Transaction;
import tn.iteam.springpersofinance.service.CategoryService;
import tn.iteam.springpersofinance.service.TransactionService;

@Controller
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @GetMapping("/get-add-trans")
    public String getAddTransaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("content", "transactions/add_transaction");
        return "layouts/layouts";
    }
}
