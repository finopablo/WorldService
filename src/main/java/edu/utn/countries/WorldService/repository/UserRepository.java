package edu.utn.countries.WorldService.repository;

import edu.utn.countries.WorldService.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsernameAndPassword(String username , String password);
}
