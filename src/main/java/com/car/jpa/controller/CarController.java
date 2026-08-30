package com.car.jpa.controller;

import com.car.jpa.model.Car;
import com.car.jpa.model.Category;
import com.car.jpa.model.Country;
import com.car.jpa.repository.CarRepository;
import com.car.jpa.repository.CategoriesRepository;
import com.car.jpa.repository.CountryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final CountryRepository countryRepository;
    private final CategoriesRepository categoriesRepository;
    @GetMapping(value = "/addcar")
    public String addCarPage(Model model){
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "add-car";
    }


    @PostMapping(value = "/addcar")
    public String addCar(@RequestParam(name = "car_name") String name,
                         @RequestParam(name= "car_model") String model,
                         @RequestParam(name = "car_year") int year,
                         @RequestParam(name = "car_price") int price,

                         @RequestParam(name= "country_id") Long countryId
    ){
        Country country= countryRepository.findById(countryId).orElse(null);
        if (country!=null){
            Car car= Car.builder()
                    .name(name)
                    .model(model)
                    .year(year)
                    .price(price)
                    .country(country)
                    .build();
            carRepository.save(car);
            return "redirect:/";
        }return "redirect:/addcar?countryNotFound";
    }


    @GetMapping(value = "/")
    public String carsPage(Model model){
        List<Car> cars= carRepository.findAll();
        model.addAttribute("cars", cars);
        List<Country> countries=countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "cars";
    }


    @GetMapping(value = "/car")
    public String carById(@RequestParam(name = "id") Long id,
                          Model model){
      Car car= carRepository.findById(id).orElse(null);

      if (car!=null){
          model.addAttribute("car",car);
          List<Category> categories= categoriesRepository.findAll();
          if (car.getCategories()!=null){
              categories.removeAll(car.getCategories());
          }
          model.addAttribute("categories", categories);
          return "details";
      }else {
          return "redirect:/404";
      }
    }


    @GetMapping(value = "/404")
    public String carNotFoundPage(){
        return "404";
    }


    @GetMapping("/updatecar")
    public String editCarPage(@RequestParam(name = "id") Long id,
                              Model model){
        Car car = carRepository.findById(id).orElse(null);
        if (car!=null){
            model.addAttribute("car", car);
            List<Country> countries=countryRepository.findAll();
            model.addAttribute("countries", countries);
            return "edit-car";
        }else {
            return "redirect:/404";
        }
    }


    @PostMapping("/updatecar")
    public String editCar(@RequestParam(name = "car_id") Long id,
                          @RequestParam(name = "car_name") String name,
                          @RequestParam(name= "car_model") String model,
                          @RequestParam(name = "car_year") int year,
                          @RequestParam(name = "car_price") int price,
                          @RequestParam(name="country_id") Long countryId ){
       Car car =carRepository.findById(id).orElse(null);
       if (car !=null){
           Country country=countryRepository.findById(countryId).orElse(null);
           if (country!=null){
               car.setName(name);
               car.setModel(model);
               car.setYear(year);
               car.setPrice(price);
               car.setCountry(country);
               carRepository.save(car);
               return "redirect:/";
           }
       }return "redirect:/404";
    }


    @PostMapping(value = "/deletecar")
    public String deleteCar(@RequestParam("car_id") Long id){
      carRepository.deleteById(id);
      return "redirect:/";
    }


    @PostMapping("/assigncategory")
    public String assignCategory(@RequestParam(name = "car_id") Long carId,
                                 @RequestParam(name = "category_id") Long categoryId){
        Car car=carRepository.findById(carId).orElse(null);
        if (car!=null){
           List<Category> categories=car.getCategories();
           if (categories==null){
               categories=new ArrayList<>();
           }
           Category category=categoriesRepository.findById(categoryId).orElse(null);
           categories.add(category);
           car.setCategories(categories);
           carRepository.save(car);
           return "redirect:/car?id=" + car.getId();
        }
        return "redirect:/404";
    }
    @PostMapping("/unassigncategory")
    public String unassignCategory(@RequestParam(name = "car_id") Long carId,
                                 @RequestParam(name = "category_id") Long categoryId){
        Car car=carRepository.findById(carId).orElse(null);
        if (car!=null){
            List<Category> categories=car.getCategories();
            if (categories==null){
                categories=new ArrayList<>();
            }
            Category category=categoriesRepository.findById(categoryId).orElse(null);
            categories.remove(category);
            car.setCategories(categories);
            carRepository.save(car);
            return "redirect:/car?id=" + car.getId();
        }
        return "redirect:/404";
    }
}
