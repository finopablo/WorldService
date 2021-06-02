package edu.utn.countries.WorldService.controller;

import edu.utn.countries.WorldService.domain.Country;
import edu.utn.countries.WorldService.service.CountryService;
import edu.utn.countries.WorldService.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CountryControllerTest {

    private CountryService countryService;
    private StateService stateService;

    private CountryController countryController;

    private static List<Country> EMPTY_LIST = Collections.emptyList();
    private static List<Country> COUNTRY_LIST =  List.of(Country.builder().name("Argentina").code("AR").build(),
                Country.builder().name("Brasil").code("BR").build());


    @BeforeEach
    public void setUp() {
        countryService = mock(CountryService.class);
        stateService = mock(StateService.class);
        countryController = new CountryController(countryService, stateService);
    }

    /**
     * Vamos a testear que haya paises dentro de lo que se pidio
     */
    @Test
    public void testAllCountriesHttpStatus200() {

        //given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Country> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(COUNTRY_LIST);
        when(countryService.allCountries(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Country>> response = countryController.allCountries(pageable);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)) );
        assertEquals(10, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)) );
        assertEquals(COUNTRY_LIST, response.getBody());
    }


    /**
     * Vamos a testear que haya paises dentro de lo que se pidio
     */
    @Test
    public void testAllCountriesNoContent() {
        //given
        Pageable pageable = PageRequest.of(50, 10);
        Page<Country> mockedPage = mock(Page.class);
        when(mockedPage.getContent()).thenReturn(EMPTY_LIST);
        when(countryService.allCountries(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Country>> response = countryController.allCountries(pageable);

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }


}
