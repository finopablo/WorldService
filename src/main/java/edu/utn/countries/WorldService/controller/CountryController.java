package edu.utn.countries.WorldService.controller;

import edu.utn.countries.WorldService.domain.Country;
import edu.utn.countries.WorldService.dto.StateDto;
import edu.utn.countries.WorldService.exceptions.CountryExistsException;
import edu.utn.countries.WorldService.exceptions.CountryNotExistsException;
import edu.utn.countries.WorldService.service.CountryService;
import edu.utn.countries.WorldService.service.StateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/countries")
public class CountryController {


    private final CountryService countryService;
    private final StateService stateService;
    private final ModelMapper modelMapper;

    @Autowired
    public CountryController(CountryService countryService, StateService stateService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.stateService = stateService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(produces = "application/json", params = {"startWith"})
    public ResponseEntity<List<Country>> countriesByNameLike(@RequestParam("startWith") String name) {
        List<Country> filteredList = countryService.filterCountries(name);
        return ResponseEntity.status(filteredList.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(filteredList);
    }


    @GetMapping(produces = "application/json", params = {"name"})
    public ResponseEntity<List<Country>> countriesByName(@RequestParam("name") String name) {
        List<Country> filteredList = countryService.filterCountriesByName(name);
        return response(filteredList);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Country>> allCountries(Pageable pageable) {
        Page p = countryService.allCountries(pageable);
        return ResponseEntity.
                status(HttpStatus.OK).
                header("X-Total-Count", Long.toString(p.getTotalElements())).
                header("X-Total-Pages", Long.toString(p.getTotalPages())).
                body(p.getContent());
    }

    @GetMapping(value = "{code}", produces = "application/json")
    public ResponseEntity<Country> countriesByCode(@PathVariable("code") String code) throws CountryNotExistsException {
        Country country = countryService.getCountryByCode(code);
        return ResponseEntity.ok(country);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newCountry(@RequestBody Country country) throws CountryExistsException {
        Country newCountry = countryService.newCountry(country);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCountry.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "{code}/states", produces = "application/json")
    public ResponseEntity<List<StateDto>> statesByCountry(@PathVariable("code") String countryCode) {
         List<StateDto> stateList = stateService.findStatesByCountry(countryCode).stream().map(st -> StateDto.builder().id(st.getId()).name(st.getName()).build()).collect(Collectors.toList());
        return response(stateList);
    }



    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

}
