package tn.iteam.springpersofinance.service;

import org.hibernate.TransactionException;
import org.springframework.stereotype.Service;
import tn.iteam.springpersofinance.dto.TransactionDto;
import tn.iteam.springpersofinance.entities.Account;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.Transaction;
import tn.iteam.springpersofinance.interfaces.TransactionInterface;
import tn.iteam.springpersofinance.repositories.TransactionRepository;
import tn.iteam.springpersofinance.exceptions.TransEception;
import java.util.List;

import static tn.iteam.springpersofinance.enums.TransactionType.*;

@Service
public class TransactionService implements TransactionInterface {
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final AccountsService accountsService;

    public TransactionService(TransactionRepository transactionRepository, CategoryService categoryService, AccountsService accountsService) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
        this.accountsService = accountsService;
    }

    @Override
    public void addTransaction(Transaction transaction, String categoryName, Long categoryId, Long sourceAccountId, Long targetAccountId) throws TransEception {
        try {
            // Validate transaction object
            if (transaction == null) {
                throw new TransEception("Transaction cannot be null.");
            }

            // Validate category inputs
            if (categoryId == null || categoryId <= 0) {
                throw new TransEception("Invalid category ID provided.");
            }
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new TransEception("Category name cannot be empty.");
            }

            // Fetch and validate the category
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                throw new TransEception("No category found for ID: " + categoryId);
            }
            if (!category.getName().equalsIgnoreCase(categoryName)) {
                throw new TransEception("Provided category name does not match the category ID.");
            }

            // Fetch and validate source account
            Account sourceAccount = accountsService.getAccount(sourceAccountId);
            if (sourceAccount == null) {
                throw new TransEception("Source account not found for ID: " + sourceAccountId);
            }

            // Fetch and validate target account
            Account targetAccount = accountsService.getAccount(targetAccountId);
            if (targetAccount == null) {
                throw new TransEception("Target account not found for ID: " + targetAccountId);
            }

            // Assign the category and accounts to the transaction
            transaction.setCategory(category);
            transaction.setSourceAccount(sourceAccount);
            transaction.setTargetAccount(targetAccount);

            // Save the transaction
            transactionRepository.save(transaction);
        } catch (TransEception e) {
            // Rethrow custom exceptions to the caller
            throw e;
        } catch (Exception e) {
            // Wrap other exceptions in a custom exception
            throw new TransEception("Error occurred while adding the transaction: " + e.getMessage());
        }
    }

    /*    public Transaction processTransaction(TransactionDto transactionDto) {
        // Fetch the source account if applicable
        Account sourceAccount = null;
        if (transactionDto.getSourceAccountId() != null) {
            sourceAccount = accountsService.getAccount(transactionDto.getSourceAccountId());
                    //.orElseThrow(() -> new RuntimeException("Source account not found"));
        }

        // Fetch the target account if applicable
        Account targetAccount = null;
        if (transactionDto.getTargetAccountId() != null) {
            targetAccount = accountsService.getAccount(transactionDto.getSourceAccountId());
                    //.orElseThrow(() -> new RuntimeException("Target account not found"));
        }

        // Process based on transaction type
        switch (transactionDto.getType()) {
            case INCOME:
                if (targetAccount == null) throw new RuntimeException("Target account is required for income");
                targetAccount.setBalance(targetAccount.getBalance() + transactionDto.getAmount());
                accountsService.createAccount(targetAccount);
                //accountRepository.save(targetAccount);
                break;

            case EXPENSE:
                if (sourceAccount == null) throw new RuntimeException("Source account is required for expense");
                if (sourceAccount.getBalance() < transactionDto.getAmount()) {
                    throw new RuntimeException("Insufficient funds in source account");
                }
                sourceAccount.setBalance(sourceAccount.getBalance() - transactionDto.getAmount());
                accountsService.createAccount(targetAccount);
                break;

            case TRANSFER:
                if (sourceAccount == null || targetAccount == null) {
                    throw new RuntimeException("Both source and target accounts are required for transfer");
                }
                if (sourceAccount.equals(targetAccount)) {
                    throw new RuntimeException("Source and target accounts must be different");
                }
                if (sourceAccount.getBalance() < transactionDto.getAmount()) {
                    throw new RuntimeException("Insufficient funds in source account");
                }
                sourceAccount.setBalance(sourceAccount.getBalance() - transactionDto.getAmount());
                targetAccount.setBalance(targetAccount.getBalance() + transactionDto.getAmount());
                accountsService.createAccount(sourceAccount);
                accountsService.createAccount(targetAccount);
                break;

            default:
                throw new RuntimeException("Unsupported transaction type");
        }

        // Save transaction details
        Transaction transaction = new Transaction();
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transactionRepository.save(transaction);

        return transaction;
    }*/
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
        try{
            return transactionRepository.findAll();
        }catch (TransactionException e) {
            throw new TransEception(e.getMessage());
        }
    }

    @Override
    public List<Transaction> getByname(String name) {
        return List.of();
    }
}
