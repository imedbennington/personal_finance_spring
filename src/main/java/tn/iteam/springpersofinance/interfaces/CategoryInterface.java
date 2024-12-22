package tn.iteam.springpersofinance.interfaces;

import tn.iteam.springpersofinance.entities.Category;

import java.util.List;

public interface CategoryInterface {
    void addCategory(Category category);
    void deleteCategory(Long id);
    void updateCategory(Category category);
    List<Category> getAllCategories();
    List<Category> getCategoriesByName(String nom);
    Category getCategoryById(Long id);
    Long getCategoryIDByName(String name);
}
