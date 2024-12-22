package tn.iteam.springpersofinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iteam.springpersofinance.entities.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
