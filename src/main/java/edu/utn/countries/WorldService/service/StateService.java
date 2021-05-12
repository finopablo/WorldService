package edu.utn.countries.WorldService.service;

import edu.utn.countries.WorldService.domain.State;
import edu.utn.countries.WorldService.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }


    public List<State> findStatesByCountry(String countryCode) {
        return stateRepository.findStateByCountryCode(countryCode);
    }


    public State findById(Integer id) {
        return stateRepository.findById(id).get();
    }

    public State newState(State state) {
           return stateRepository.save(state);
    }
}
