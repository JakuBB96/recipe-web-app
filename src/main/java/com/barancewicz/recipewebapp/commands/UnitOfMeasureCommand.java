package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String description;
    private IngredientCommand ingredient;
}
