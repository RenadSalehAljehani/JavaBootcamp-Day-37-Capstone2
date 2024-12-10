package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Using find
    Category findCategoryByCategoryId(Integer categoryId);

    Category findCategoryByName(String name);
}