package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
    private IngredientCommand ingredient;
}
