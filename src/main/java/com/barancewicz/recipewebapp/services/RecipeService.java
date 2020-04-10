package com.barancewicz.recipewebapp.services;


import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById (Long id);
    void deleteById(Long id);
 }
