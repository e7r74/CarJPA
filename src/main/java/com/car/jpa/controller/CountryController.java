package com.car.jpa.controller;

import com.car.jpa.model.Country;
import com.car.jpa.repository.CountryRepository;
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

    private final CountryRepository countryRepository;
    @GetMapping("/addcountry")
    public String addCountryPage(){
        return "add-country";
    }
    @PostMapping("/addcountry")
    public String addCountry(@RequestParam(name = "country_name") String countryName,
                             @RequestParam(name = "country_code") String countryCode){
        Country country= Country.builder()
                                .name(countryName)
                                .code(countryCode)
                                .build();
        countryRepository.save(country);
        return "redirect:/country";
    }
    @GetMapping("/country")
    public String getAllCountry(Model model){
        List<Country> countries= countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country";
    }

}
