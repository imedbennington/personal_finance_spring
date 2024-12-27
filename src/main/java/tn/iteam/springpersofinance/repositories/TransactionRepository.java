package tn.iteam.springpersofinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.entities.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.description LIKE %:desc%")
    List<Transaction> findByName(String desc);
}
