package tn.iteam.springpersofinance.interfaces;

import tn.iteam.springpersofinance.entities.Transaction;
import tn.iteam.springpersofinance.exceptions.TransEception;

import java.util.List;

public interface TransactionInterface {
    void addTransaction(Transaction transaction, String categoryname, Long categoryid, Long source_account, Long dest_account) throws TransEception;
    void deleteTransaction(Transaction transaction, Long id);
    void updateTransaction(Transaction transaction, Long id);
    Transaction getTransaction(Long id);
    List<Transaction> getTransactions();
    List<Transaction> getByname(String name);
}
