package com.car.jpa.except;

public class CarNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Car not found!";
    }
}
