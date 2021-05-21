package edu.utn.countries.WorldService.controller;

import edu.utn.countries.WorldService.exceptions.ErrorMessage;
import edu.utn.countries.WorldService.exceptions.GenericWebException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CountryControllerAdvice {

    @ExceptionHandler(value = {GenericWebException.class})
    public ResponseEntity<ErrorMessage> countryExists(GenericWebException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ErrorMessage.builder().code("").message(ex.getMessage()).build());
    }

}
