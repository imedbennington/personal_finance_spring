package tn.iteam.springpersofinance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tn.iteam.springpersofinance.entities.Category;
import tn.iteam.springpersofinance.service.CategoryService;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
@Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-add-category")
    public String getAddCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("content", "categories_views/add_category");
        return "layouts/layouts";
    }

    @GetMapping("/get-categories")
    public String getCategories(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories()); // Make sure you have this method in your service
        model.addAttribute("content", "categories_views/categories");
        return "layouts/layouts";
    }
    @PostMapping("/categories-add")
    public String addCategory(@ModelAttribute("category") Category category, Model model) {
        try {
            // Add the category to the database
            categoryService.addCategory(category);

            // Add a success message
            model.addAttribute("message", "Category added successfully!");

            // Redirect to the categories list page (adjust the redirect URL as needed)
            return "redirect:/get-categories";  // Redirect to the categories list page or your desired page
        } catch (Exception e) {
            // In case of an error, show an error message
            model.addAttribute("errorMessage", "Failed to add category. Please try again.");
            return "layouts/layouts";  // Return to the form page to display the error message
        }
    }

    @GetMapping("/categories/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id); // Fetch category by ID
        model.addAttribute("category", category);
        model.addAttribute("content", "categories_views/update_category");
        return "layouts/layouts";// Return the view for updating category
    }

    @PostMapping("/categories/update")
    public String updateCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(category);
        redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
        return "redirect:/get-categories"; // Redirect back to the category list
    }



    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id); // Delete category by ID
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully!"); // Add success message
        return "redirect:/get-categories"; // Redirect to categories list page
    }


}
