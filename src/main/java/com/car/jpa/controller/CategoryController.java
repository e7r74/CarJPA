package com.car.jpa.controller;

import com.car.jpa.model.Category;
import com.car.jpa.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoriesRepository categoriesRepository;

    @GetMapping
    public String categoryPage(Model model){
        model.addAttribute("categories", categoriesRepository.findAll());
        return "categories";
    }
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id,
                               Model model){
       Category category= categoriesRepository.findById(id).orElse(null);
            if (category!=null){
                model.addAttribute("category",category);
                return "detcategory";
            }
        return "404";
    }
    @PostMapping("/addcategory")
    public String addCategory(@RequestParam(name = "category_name") String name){
        Category category=Category.builder()
                                   .name(name)
                                   .build();
        categoriesRepository.save(category);
        return "redirect:/category";
    }
    @GetMapping("/delcategory/{id}")
    public String delCategory(@PathVariable("id") Long id){
        categoriesRepository.deleteById(id);
        return "redirect:/category";
    }
    @PostMapping("/updatecat/{id}")
    public String updateCat(@PathVariable("id") Long id,
                            @RequestParam(name = "category_name")String name){
        Category category=categoriesRepository.findById(id).orElse(null);
        if (category!=null){
            category.setName(name);
            categoriesRepository.save(category);
            return "redirect:/category";
        }
        return "404";
    }


}
