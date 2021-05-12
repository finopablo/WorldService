package edu.utn.countries.WorldService.repository;

import edu.utn.countries.WorldService.domain.Country;
import edu.utn.countries.WorldService.domain.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {
   List<State> findStateByCountryCode(String countryCode);
}
