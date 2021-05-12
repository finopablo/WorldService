package edu.utn.countries.WorldService.repository;

import edu.utn.countries.WorldService.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CountryRepository  extends CrudRepository<Country, String> {
   List<Country> findByNameStartsWith(String name);
   Page<Country> findAll(Pageable pageable);
   List<Country> findByName(String name);
}
