package com.car.jpa.services.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CategoriesRepository categoriesRepository;
    private final CountryRepository countryRepository;
    public Category getCategory(Long id){
        return categoriesRepository.findById(id).orElse(null);
    }
    public Country getCountry(Long id){
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCar(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car addCar(Car car) throws CountryNotFoundException {
        Country country= getCountry(car.getCountry().getId());
        if (Objects.isNull(country)){
            throw new CountryNotFoundException();
        }
        return carRepository.save(car);
    }

    @Override
    public Car updeteCar(Car car) throws CarNotFoundException, CountryNotFoundException {
//        Car car1=getCar(car.getId());
//        car1.setName(car.getName());
//        car1.setModel(car.getModel());
//        car1.setYear(car.getYear());
//        car1.setPrice(car.getPrice());
          Car car1=getCar(car.getId());
          if (car1==null){
              throw new CarNotFoundException();
          }
        if (car.getCountry() == null || car.getCountry().getId() == null) {
            throw new CountryNotFoundException();
        }
          Country country=getCountry(car.getCountry().getId());
          if(country==null){
              throw new CountryNotFoundException();
          }
          return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) throws CarNotFoundException {
        Car car =getCar(id);
        if (car==null){
            throw new CarNotFoundException();
        }else {
            carRepository.deleteById(id);
        }
    }

    @Override
    public void assignCategory(Long carId, Long categoryId) throws CarNotFoundException, CategoryNotFoundException {
        Car carCheck= getCar(carId);
        if (carCheck==null){
            throw new CarNotFoundException();
        }
        Category categoryCheck= getCategory(categoryId);
        if (categoryCheck==null){
            throw new CategoryNotFoundException();
        }
        List<Category> categories= carCheck.getCategories();
        if (categories==null){
            categories=new ArrayList<>();
        }
        categories.add(categoryCheck);
        carCheck.setCategories(categories);

        carRepository.save(carCheck);
    }

    @Override
    public void unassignCategory(Long carId, Long categoryId) throws CarNotFoundException, CategoryNotFoundException {
        Car carCheck= getCar(carId);
        if (carCheck==null){
            throw new CarNotFoundException();
        }
        Category categoryCheck= getCategory(categoryId);
        if (categoryCheck==null){
            throw new CategoryNotFoundException();
        }
        List<Category> categories= carCheck.getCategories();
        if (categories==null){
            categories=new ArrayList<>();
        }
        categories.remove(categoryCheck);
        carCheck.setCategories(categories);

        carRepository.save(carCheck);
    }

    @Override
    public List<Category> loadCategoriesThatDoesNotBelongToCar(Long carId) throws CarNotFoundException{
        List<Category> categories= categoriesRepository.findAll();

        Car car= getCar(carId);
       if (car==null){
            throw new CarNotFoundException();
       }

       categories.removeAll(car.getCategories());
       return categories;
    }

    @Override
    public Page<Car> getCarsPageWithSortAndSearch(int pageNumber, int pageSize,String namePart, String sortBy, String sortOrder) {
        PageRequest pageRequest;

        if (Objects.isNull(sortBy) || sortBy.isEmpty() || sortBy.equals("null")) {
            pageRequest = PageRequest.of(pageNumber, pageSize);
        } else {
            Sort sort = Sort.by(
                    sortOrder.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                    sortBy
            );

            pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        }

        if (Objects.isNull(namePart) || namePart.isEmpty()) {
            return carRepository.findAll(pageRequest);
        }

        Specification<Car> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name"), "%" + namePart + "%");
            Predicate p2 = criteriaBuilder.like(root.get("model"), "%" + namePart + "%");

            return criteriaBuilder.or(p1, p2);
        };

        return carRepository.findAll(specification, pageRequest);
    }

    @Override
    public List<Integer> generatePageNumbersList(int totalPages) {
        return IntStream.range(0,totalPages).boxed().toList();
    }
}
