package com.car.jpa.services;

import com.car.jpa.except.CountryNotFoundException;
import com.car.jpa.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> getCountries();
    Country getCountry(Long id);
    Country addCountry(Country country);
    Country updateCountry(Country country) throws CountryNotFoundException;
    void delete(Long id) throws CountryNotFoundException;
}
