package edu.utn.countries.WorldService.controller;

import edu.utn.countries.WorldService.domain.Country;
import edu.utn.countries.WorldService.domain.State;
import edu.utn.countries.WorldService.dto.StateDto;
import edu.utn.countries.WorldService.exceptions.CountryExistsException;
import edu.utn.countries.WorldService.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/states")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping(value = "byCountry", produces = "application/json")
    public ResponseEntity<List<StateDto>> getStatesByCountry(@RequestParam("code") String countryCode) {
        List<State> states = stateService.findStatesByCountry(countryCode);
        List<StateDto> stateDtos =
                    states.stream().map(o -> StateDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList());
        return ResponseEntity.status(stateDtos .size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(stateDtos);
    }


    @GetMapping(value = "byId", produces = "application/json")
    public ResponseEntity<StateDto> getStatesByCountry(@RequestParam("id") Integer id) {
        State state = stateService.findById(id);
        return ResponseEntity.ok(StateDto.from(state));
    }

    @PostMapping(value = "new", consumes = "application/json")
    public ResponseEntity newState(@RequestBody StateDto stateDto) {
        stateService.newState(
                                State.builder().name(stateDto.getName()).
                                        country(Country.builder().name(stateDto.getCountry().getName()).code(stateDto.getCountry().getCode()).build()).
                                        build());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
