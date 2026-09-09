package com.car.jpa.except;

public class CountryNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Country no found!";
    }
}
