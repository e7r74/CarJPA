package com.car.jpa.repository.custom;


import com.car.jpa.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomCarRepository{
//    List<Car> findAllByCriteria(int year);
    List<Car> findAllByCriteria(String name, Integer year, Integer price, Long countryId, Long categoryId);
}
