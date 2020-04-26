package com.barancewicz.recipewebapp.repositories;


import com.barancewicz.recipewebapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}