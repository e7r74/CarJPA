package com.car.jpa.services;

import com.car.jpa.except.CarNotFoundException;
import com.car.jpa.except.CategoryNotFoundException;
import com.car.jpa.except.CountryNotFoundException;
import com.car.jpa.model.Car;
import com.car.jpa.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CarService {
    List<Car> getCars();
    Car getCar(Long id);
    Car addCar(Car car) throws CountryNotFoundException;
    Car updeteCar(Car car) throws CarNotFoundException, CountryNotFoundException;
    void deleteCar(Long id) throws  CarNotFoundException;
    void assignCategory(Long carId,Long categoryId)throws CarNotFoundException, CategoryNotFoundException;
    void unassignCategory(Long carId, Long categoryId) throws CarNotFoundException, CategoryNotFoundException;
    List<Category> loadCategoriesThatDoesNotBelongToCar(Long carId) throws CarNotFoundException;
    Page<Car> getCarsPageWithSortAndSearch(int pageNumber, int pageSize,String namePart, String sortBy, String sortOrder);
    List<Integer> generatePageNumbersList(int totalPages);
}
