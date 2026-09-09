package com.car.jpa.services.impl;

import com.car.jpa.except.CategoryNotFoundException;
import com.car.jpa.model.Category;
import com.car.jpa.repository.CategoriesRepository;
import com.car.jpa.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<Category> getCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @Override
    public Category addCategory(Category category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) throws CategoryNotFoundException {
        Category category1= getCategory(category.getId());
        category1.setName(category.getName());
        return categoriesRepository.save(category1);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        Category category =getCategory(id);
        if (category==null){
            throw new CategoryNotFoundException();
        }else {
            categoriesRepository.deleteById(id);
        }
    }
}
