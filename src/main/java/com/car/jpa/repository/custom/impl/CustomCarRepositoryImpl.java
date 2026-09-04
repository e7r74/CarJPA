package com.car.jpa.repository.custom.impl;

import com.car.jpa.model.Car;
import com.car.jpa.model.Category;
import com.car.jpa.model.Country;
import com.car.jpa.repository.custom.CustomCarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCarRepositoryImpl implements CustomCarRepository {
    private final EntityManager entityManager;

    @Override
    public List<Car> findAllByCriteria(String name, Integer year, Integer price, Long countryId, Long categoryId) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery= criteriaBuilder.createQuery(Car.class);
        Root<Car> root=criteriaQuery.from(Car.class);
        List<Predicate> predicates=new ArrayList<>();
        if (year!=null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"),year));
        }
        if (name!=null){
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }
        if (price!=null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price));
        }
        if (countryId!=null){
            Join<Car, Country> countryJoin= root.join("country");
            predicates.add(criteriaBuilder.equal(countryJoin.get("id"),countryId));
        }
        if (categoryId!=null){
            Join<Car, Category> categoryJoin=root.join("categories");
            predicates.add(criteriaBuilder.equal(categoryJoin.get("id"),categoryId));
        }
        if (!predicates.isEmpty()){
           criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<Car> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
