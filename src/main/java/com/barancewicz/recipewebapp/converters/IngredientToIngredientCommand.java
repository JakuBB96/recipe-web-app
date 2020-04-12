package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.IngredientCommand;
import com.barancewicz.recipewebapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {


    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source==null){
            return null;
        }
        final IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());
        command.setAmount(source.getAmount());
        if (source.getRecipe() != null){
            command.setRecipeId(source.getRecipe().getId());
        }
        command.setDescription(source.getDescription());
        command.setUom(unitOfMeasureToUnitOfMeasureCommand.convert(source.getUom()));
        return command;
    }
}
