package edu.utn.countries.WorldService.exceptions;

import org.springframework.http.HttpStatus;

public class CountryNotExistsException extends GenericWebException {

    public CountryNotExistsException() {
        this.status = HttpStatus.NOT_FOUND.value();
        this.code = "01";
    }

    public String getMessage() {
        return "Country Not Exists";
    }
}
