package com.car.jpa.controller;

import com.car.jpa.except.CarNotFoundException;
import com.car.jpa.except.CategoryNotFoundException;
import com.car.jpa.except.CountryNotFoundException;
import com.car.jpa.model.Car;
import com.car.jpa.model.Category;
import com.car.jpa.model.Country;
import com.car.jpa.repository.CarRepository;
import com.car.jpa.repository.CategoriesRepository;
import com.car.jpa.repository.CountryRepository;
import com.car.jpa.services.CarService;
import com.car.jpa.services.CategoryService;
import com.car.jpa.services.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import  org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CountryService countryService;
    private final CategoryService categoryService;

    @GetMapping(value = "/addcar")
    public String addCarPage(Model model){
        List<Country> countries=countryService.getCountries();
        model.addAttribute("countries", countries);
        return "add-car";
    }


    @PostMapping(value = "/addcar")
    public String addCar(Car car){
        try {
            carService.addCar(car);
            return "redirect:/";
        }catch (CountryNotFoundException e){
            return "redirect:/addcar?countryNotFound";
        }
    }
//==============================================
/////////////AllCustomCarRepository//////////////
//==============================================
//    @GetMapping(value = "/")
//    public String carsPage(Model model,
//                            @RequestParam(name = "year", required = false) Integer year,
//                           @RequestParam(name="name",required = false)String name,
//                            @RequestParam(name="price", required = false) Integer price,
//                           @RequestParam(name="country_id", required = false)Long countryId,
//                           @RequestParam(name="category_id", required = false) Long categoryId){
////        List<Country> countries=countryRepository.findAll();
////        model.addAttribute("countries", countries);
////        List<Car> cars;
////
////        if (year != null && name!=null){
//////            cars = carRepository.findAllByYear(year);
//////////////////////////////////////////////
////                //CustomCarRepository
//
////              cars=carRepository.findAllByCriteria(name,year);
////        }else {
////            cars =carRepository.findAll();
////        }
//////////////////////////////////////
//        //CustomCarRepository-2
///////////////////////////////////
//        List<Car> cars=carRepository.findAllByCriteria(name,year,price,countryId,categoryId);
//        List<Country> countries=countryRepository.findAll();
//        model.addAttribute("countries", countries);
//        List<Category> categories=categoriesRepository.findAll();
//        model.addAttribute("categories", categories);
//        if (countryId!=null){
//            model.addAttribute("country_id_chek", countryId);
//        }
//        if (categoryId!= null){
//            model.addAttribute("category_id_chek", categoryId);
//        }
//        model.addAttribute("cars", cars);
//        return "cars";
//    }

////////////////////////////////////
//    Specification
////////////////////////////////////
//        @GetMapping("/")
//        public String carsPage(@RequestParam(name = "name", required = false) String name,
//                               Model model){
//
//         Specification<Car> carSpecification = ((root, query, criteriaBuilder) ->
//                                                criteriaBuilder.equal(root.get("name"), name) );
//            List<Car> cars=carRepository.findAll(carSpecification);
//            model.addAttribute("cars",cars);
//            return "cars";
//        }








//==============================================



// =======================================================
    /////////////Pagination. PageRequest, Pageable, сортировка//////////////
//=======================================================
@GetMapping(value = "/")
public String carsPage(Model model,
                       @RequestParam(name="page", required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(name="size", required = false, defaultValue = "8") int pageSize,
                       @RequestParam(name="price", required = false, defaultValue = "price") String sortBy,
                       @RequestParam(name = "sort_order", required = false, defaultValue = "ASC")String sortOrder,
                       @RequestParam(name="search_key", required = false)String searchKey){
    Page<Car> carPage=carService.getCarsPageWithSortAndSearch(
            pageNumber,
            pageSize,
            searchKey,
            sortBy,
            sortOrder
    );
    List<Car> cars= carPage.getContent();

    model.addAttribute("cars",cars);
    model.addAttribute("current_page", carPage.getNumber());
    model.addAttribute("page_size",pageSize);
    model.addAttribute("total_pages",carPage.getTotalPages());
    model.addAttribute("page_numbers",carService.generatePageNumbersList(carPage.getTotalPages()));
    model.addAttribute("sort_by", sortBy);
    model.addAttribute("sort_order",sortOrder);
    model.addAttribute("search_key", searchKey);
    return "cars";
}
/////////////
    @GetMapping(value = "/car")
    public String carById(@RequestParam(name = "id") Long id,
                          Model model){
      Car car=carService.getCar(id);
      if (Objects.isNull(car)){
          return "redirect:/404";
      }
     model.addAttribute("car", car);
      try {
          List<Category> categories= carService.loadCategoriesThatDoesNotBelongToCar(id);
          model.addAttribute("categories", categories);
          return "details";
      }catch (CarNotFoundException e){
          return "redirect:/404";
      }



//      if (car!=null){
//          model.addAttribute("car",car);
//          List<Category> categories= categoryService.getCategories();
//          if (car.getCategories()!=null){
//              categories.removeAll(car.getCategories());
//          }
//          model.addAttribute("categories", categories);
//          return "details";
//      }else {
//          return "redirect:/404";
//      }
    }
/////////


    @GetMapping("/updatecar")
    public String editCarPage(@RequestParam(name = "id") Long id,
                              Model model){
//        Car car = carService.getCar(id);
//        if (car!=null){
//            model.addAttribute("car", car);
//            List<Country> countries=countryService.getCountries();
//            model.addAttribute("countries", countries);
//            return "edit-car";
//        }else {
//            return "redirect:/404";
//        }
        Car car = carService.getCar(id);
        model.addAttribute("car", car);
        List<Country> countries=countryService.getCountries();
        model.addAttribute("countries", countries);
        return "edit-car";
    }


    @PostMapping("/updatecar")
    public String editCar(Car car){
//       Car car =carService.getCar(id);
//       if (car !=null){
//           Country country=countryService.getCountry(countryId);
//           if (country!=null){
//               car.setName(name);
//               car.setModel(model);
//               car.setYear(year);
//               car.setPrice(price);
//               car.setCountry(country);
//               try {
//                   carService.updeteCar(car);
//               } catch (CarNotFoundException | CountryNotFoundException e) {
//                   return "redirect:/updatecar?error";
//               }
//           }
//       }
//       return "redirect:/";
        try {
            carService.updeteCar(car);
            return "redirect:/";
        }catch (CarNotFoundException | CountryNotFoundException e){
            return "redirect:/404";
        }
    }


    @PostMapping(value = "/deletecar")
    public String deleteCar(@RequestParam("car_id") Long id){
      try {
          carService.deleteCar(id);
          return "redirect:/";
      }catch (CarNotFoundException e){
          return "redirect:/404";
      }

    }


    @PostMapping("/assigncategory")
    public String assignCategory(@RequestParam(name = "car_id") Long carId,
                                 @RequestParam(name = "category_id") Long categoryId){
        try {
            carService.assignCategory(carId,categoryId);
            return "redirect:/car?id="+carId;
        }catch (CarNotFoundException e){
            return "redirect:/404";
        }catch (CategoryNotFoundException e){
            return "redirect:/car?id="+carId;
        }

    }
    @PostMapping("/unassigncategory")
    public String unassignCategory(@RequestParam(name = "car_id") Long carId,
                                 @RequestParam(name = "category_id") Long categoryId){
        try {
            carService.unassignCategory(carId,categoryId);
            return "redirect:/car?id="+carId;
        }catch (CarNotFoundException e){
            return "redirect:/404";
        } catch (CategoryNotFoundException e) {
           return "redirect:/car?id="+carId;
        }
    }

    @GetMapping(value = "/404")
    public String carNotFoundPage(){
        return "404";
    }

}
