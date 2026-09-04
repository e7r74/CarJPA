package com.car.jpa.repository;

import com.car.jpa.model.Car;
import com.car.jpa.repository.custom.CustomCarRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long>, CustomCarRepository {
    List<Car> findAll(Specification<Car> carSpecification);
    
//    @Query ("""
//            select c from Car c
//             where c.name = :name
//             and c.year >= :year
//            """)
//    List<Car> findAllBySomeParam(String name , int year);
}
