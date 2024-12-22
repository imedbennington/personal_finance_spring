package tn.iteam.springpersofinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.iteam.springpersofinance.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Requête JPQL pour rechercher des catégories par leur nom
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:nom%")
    List<Category> findByName(String nom);
    @Query("SELECT c.id FROM Category c WHERE c.name = :categoryName")
    Long findIdByName(@Param("categoryName") String categoryName);
}
