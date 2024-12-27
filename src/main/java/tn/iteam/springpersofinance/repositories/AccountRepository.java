package tn.iteam.springpersofinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.iteam.springpersofinance.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
