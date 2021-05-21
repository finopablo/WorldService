package edu.utn.countries.WorldService.exceptions;

import org.springframework.http.HttpStatus;

public class CountryExistsException extends GenericWebException {

    public CountryExistsException() {
        this.status = HttpStatus.CONFLICT.value();
        this.code = "02";
    }

    public String getMessage() {
        return "Country Exists";
    }

}
