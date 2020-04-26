package com.barancewicz.recipewebapp.repositories;

import com.barancewicz.recipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByDescriptionLike(String description);
}
