package edu.utn.countries.WorldService.exceptions;


import lombok.Data;

@Data
public abstract class GenericWebException extends Exception {
        int status;
        String code;
}
