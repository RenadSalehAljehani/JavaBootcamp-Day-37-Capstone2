package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.Category;
import com.example.handmadetrail.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    // 1. Declare a dependency for UserRepository using Dependency Injection
    private final CategoryRepository categoryRepository;

    // 2. CRUD
    // 2.1 Get
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 2.2 Post
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // 2.3 Update
    public void updateCategory(Integer categoryId, Category category) {
        // 1. check if the category to be updated exists
        Category oldCategory = categoryRepository.findCategoryByCategoryId(categoryId);
        if (oldCategory == null) {
            throw new ApiException("Category Not Found.");
        }
        // 2. Set new values
        oldCategory.setName(category.getName());

        // 3. Save changes
        categoryRepository.save(oldCategory);
    }

    // 2.4 Delete
    public void deleteCategory(Integer categoryId) {
        // 1. check if the category to be deleted exists
        Category oldCategory = categoryRepository.findCategoryByCategoryId(categoryId);
        if (oldCategory == null) {
            throw new ApiException("Category Not Found.");
        }
        categoryRepository.delete(oldCategory);
    }

    // 3. Extra endpoints
    // An endpoint to get category by name
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name);
        if (categoryRepository.findCategoryByName(name) == null) {
            throw new ApiException("Category Not Found.");
        }
        return category;
    }
}