package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryID);

    Category updateCategory(Category category, Long categoryId);
}
