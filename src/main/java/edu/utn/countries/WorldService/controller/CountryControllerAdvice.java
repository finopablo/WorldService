package edu.utn.countries.WorldService.controller;

import edu.utn.countries.WorldService.exceptions.CountryExistsException;
import edu.utn.countries.WorldService.exceptions.CountryNotExistsException;
import edu.utn.countries.WorldService.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CountryControllerAdvice {

    @ExceptionHandler(value = {CountryExistsException.class})
    public ResponseEntity<ErrorMessage> countryExists() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessage.builder().code("CE").message("Country Already Exists!!!!").build());
    }

    @ExceptionHandler(value = {CountryNotExistsException.class})
    public ResponseEntity countryNotExists() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
