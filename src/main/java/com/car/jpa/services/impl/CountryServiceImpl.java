package com.car.jpa.services.impl;

import com.car.jpa.except.CountryNotFoundException;
import com.car.jpa.model.Country;
import com.car.jpa.repository.CountryRepository;
import com.car.jpa.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    @Override
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(Long id) {
    return countryRepository.findById(id).orElse(null);
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Country country) throws CountryNotFoundException {
       Country country1=getCountry(country.getId());

       country1.setName(country.getName());
       country1.setCode(country.getCode());
       return countryRepository.save(country1);


    }

    @Override
    public void delete(Long id) throws CountryNotFoundException {
        Country country= getCountry(id);
        if (Objects.isNull(country)) {
            throw new CountryNotFoundException();
        }else{
            countryRepository.deleteById(id);
        }
    }
}
