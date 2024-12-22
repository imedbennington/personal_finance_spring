package tn.iteam.springpersofinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.iteam.springpersofinance.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
   @Query("select u from User u where u.email = :mail")
    User findByemail(String mail);
}
