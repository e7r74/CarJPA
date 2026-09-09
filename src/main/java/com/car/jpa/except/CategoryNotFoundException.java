package com.car.jpa.except;

public class CategoryNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Category not found!";
    }
}
