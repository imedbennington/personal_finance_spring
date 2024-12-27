package tn.iteam.springpersofinance.service;

import org.hibernate.TransactionException;
import org.springframework.stereotype.Service;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.Transaction;
import tn.iteam.springpersofinance.interfaces.TransactionInterface;
import tn.iteam.springpersofinance.repositories.TransactionRepository;
import tn.iteam.springpersofinance.exceptions.TransEception;
import java.util.List;
@Service
public class TransactionService implements TransactionInterface {
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void addTransaction(Transaction transaction, String categoryName, Long categoryId) throws TransEception {
        try {
            // Validate inputs
            if (transaction == null) {
                throw new IllegalArgumentException("Transaction cannot be null.");
            }
            if (categoryId == null || categoryId <= 0) {
                throw new IllegalArgumentException("Invalid category ID.");
            }
            if (categoryName == null || categoryName.isEmpty()) {
                throw new IllegalArgumentException("Category name cannot be empty.");
            }

            // Fetch the category
            Category category = categoryService.getCategoryById(categoryId);
            if (!category.getName().equalsIgnoreCase(categoryName)) {
                throw new TransEception("Category name does not match the category ID.");
            }

            // Assign the category to the transaction
            transaction.setCategory(category);

            // Save the transaction
            transactionRepository.save(transaction);
        } catch (IllegalArgumentException e) {
            throw new TransEception("Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new TransEception("An error occurred while adding the transaction: " + e.getMessage());
        }
    }


    @Override
    public void deleteTransaction(Transaction transaction, Long id) {

    }

    @Override
    public void updateTransaction(Transaction transaction, Long id) {

    }

    @Override
    public Transaction getTransaction(Long id) {
        return null;
    }

    @Override
    public List<Transaction> getTransactions() {
        return List.of();
    }

    @Override
    public List<Transaction> getByname(String name) {
        return List.of();
    }
}
