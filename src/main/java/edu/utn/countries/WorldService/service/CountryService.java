package edu.utn.countries.WorldService.service;

import edu.utn.countries.WorldService.domain.Country;
import edu.utn.countries.WorldService.exceptions.CountryExistsException;
import edu.utn.countries.WorldService.exceptions.CountryNotExistsException;
import edu.utn.countries.WorldService.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public Country getCountryByCode(String code) throws CountryNotExistsException {
        return countryRepository.findById(code).orElseThrow(CountryNotExistsException::new);
    }

    public Page<Country> allCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public List<Country> filterCountries(String name) {
        return countryRepository.findByNameStartsWith(name);
    }

    public Page<Country> allCountriesPage(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country newCountry(Country country) throws CountryExistsException {
        if (!countryRepository.existsById(country.getCode())) {
            return countryRepository.save(country);
        } else {
            throw new CountryExistsException();
        }
    }


    public Page<Country> filterCountriesByName(String name, Pageable pageable) {
        return countryRepository.findByName(name, pageable);
    }
}
