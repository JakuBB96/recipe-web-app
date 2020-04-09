package com.barancewicz.recipewebapp.repositories;

import com.barancewicz.recipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
