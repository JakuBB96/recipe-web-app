package com.barancewicz.recipewebapp.repositories;

import com.barancewicz.recipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
