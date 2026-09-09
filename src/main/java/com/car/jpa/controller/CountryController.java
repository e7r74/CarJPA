package com.car.jpa.controller;

import com.car.jpa.model.Country;
import com.car.jpa.repository.CountryRepository;
import com.car.jpa.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    private final CountryRepository countryRepository;
    @GetMapping("/addcountry")
    public String addCountryPage(){
        return "add-country";
    }
    @PostMapping("/addcountry")
    public String addCountry(Country country){
        countryService.addCountry(country);
        return "redirect:/contry";
    }
    @GetMapping("/country")
    public String getAllCountry(Model model){
        List<Country> countries=countryService.getCountries();
        model.addAttribute("countries", countries);
        return "country";
    }

}
