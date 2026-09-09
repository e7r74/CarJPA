package com.car.jpa.services;

import com.car.jpa.except.CategoryNotFoundException;
import com.car.jpa.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category) throws CategoryNotFoundException;
    void deleteCategory(Long id) throws  CategoryNotFoundException;
}
