package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.IngredientCommand;
import com.barancewicz.recipewebapp.domain.Ingredient;

public interface IngredientService{
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeId, Long id);
}
