package tn.iteam.springpersofinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.interfaces.CategoryInterface;
import tn.iteam.springpersofinance.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryInterface {

    private final CategoryRepository categoryRepository;

    // Injecting CategoryRepository to interact with the database
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(Category category) {
        // Save a new category to the database
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // Check if the category exists
        if (categoryRepository.existsById(id)) {
            // Delete the category by its ID
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " does not exist.");
        }
    }

    @Override
    public void updateCategory(Category category) {
        // Update the category in the database
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found with id: " + category.getId());
        }
    }

    @Override
    public List<Category> getAllCategories() {
        // Return all categories from the database
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoriesByName(String nom) {
        // Find categories by their name using a query in the repository
        return categoryRepository.findByName(nom);
    }

    @Override
    public Category getCategoryById(Long id) {
        // Find a category by its ID
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Long getCategoryIDByName(String name) {
        Long categoryId = categoryRepository.findIdByName(name);
        if (categoryId == null) {
            throw new RuntimeException("Category not found for name: " + name);
        }
        return categoryId;
    }

}
