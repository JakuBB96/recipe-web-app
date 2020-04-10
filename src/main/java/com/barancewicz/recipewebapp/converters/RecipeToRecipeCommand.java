package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientToIngredientCommand, CategoryToCategoryCommand categoryToCategoryCommand, NotesToNotesCommand notesToNotesCommand) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source==null){
            return null;
        }
        final RecipeCommand command = new RecipeCommand();

        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setServings(source.getServings());
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setPrepTime(source.getPrepTime());
        command.setCookTime(source.getCookTime());
        command.setDirections(source.getDirections());

        command.setNotes(notesToNotesCommand.convert(source.getNotes()));

        if (source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().forEach(category -> command.getCategories().add(categoryToCategoryCommand.convert(category)));
        }
        if (source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().forEach(ingredient -> command.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return command;
    }
}
