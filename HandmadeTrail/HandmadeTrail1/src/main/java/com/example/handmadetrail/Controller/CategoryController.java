package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.Category;
import com.example.handmadetrail.Servise.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    // 1. Declare a dependency for CategoryService using Dependency Injection
    private final CategoryService categoryService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllCategories() {
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("New Category Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity updateCategory(@PathVariable Integer categoryId, @RequestBody @Valid Category category) {
        categoryService.updateCategory(categoryId, category);
        return ResponseEntity.status(200).body(new ApiResponse("Category Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(200).body(new ApiResponse("Category Deleted."));
    }

    // 3. Extra endpoint
    // An endpoint to get category by name
    @GetMapping("/getCategoryByName/{name}")
    public ResponseEntity getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.status(200).body(category);
    }
}