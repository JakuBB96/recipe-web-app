package com.barancewicz.recipewebapp.services;


import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Set<RecipeCommand> getRecipesCommands();
    Set<RecipeCommand> getUserRecipes(String username);
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    List<RecipeCommand> findAllByDescriptionLike(String description);
    RecipeCommand findCommandById (Long id);
    void deleteById(Long id);
 }
