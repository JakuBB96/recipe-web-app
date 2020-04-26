package com.barancewicz.recipewebapp.repositories;

import com.barancewicz.recipewebapp.domain.Role;
import com.barancewicz.recipewebapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>{
}