package com.car.jpa.controller;

import com.car.jpa.except.CategoryNotFoundException;
import com.car.jpa.model.Category;
import com.car.jpa.repository.CategoriesRepository;
import com.car.jpa.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

   private final CategoryService categoryService;

    @GetMapping
    public String categoryPage(Model model){
        model.addAttribute("categories", categoryService.getCategories());
        return "categories";
    }
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id,
                               Model model){
        Category category =categoryService.getCategory(id);
        if (Objects.isNull(category)){
            return "404";
        }
        model.addAttribute("category", category);
        return "detcategory";
    }
    @PostMapping("/addcategory")
    public String addCategory(Category category){

        categoryService.addCategory(category);
        return "redirect:/category";
    }
    @GetMapping("/delcategory/{id}")
    public String delCategory(@PathVariable("id") Long id){
        try {
            categoryService.deleteCategory(id);
            return "redirect:/category";
        }catch (CategoryNotFoundException e){
            return "redirect:/category?id="+id;
        }
    }
    @PostMapping("/updatecat/{id}")
    public String updateCat(Category category){

        try {
            categoryService.updateCategory(category);
            return "redirect:/category";
        }catch (CategoryNotFoundException e){
            return "404";
        }
    }


}
