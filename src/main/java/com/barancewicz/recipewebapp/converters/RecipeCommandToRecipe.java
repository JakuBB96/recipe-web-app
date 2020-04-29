package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    private final UserCommandToUser userCommandToUser;
    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory, IngredientCommandToIngredient ingredientCommandToIngredient, NotesCommandToNotes notesCommandToNotes, UserCommandToUser userCommandToUser) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
        this.userCommandToUser = userCommandToUser;

    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source==null){
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setServings(source.getServings());
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setDirections(source.getDirections());
        recipe.setImage(source.getImage());
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        recipe.setUser(userCommandToUser.convert(source.getUser()));
        if (source.getCategories()!=null && source.getCategories().size()>0){
            source.getCategories().forEach(categoryCommand -> recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }
        if (source.getIngredients()!=null && source.getIngredients().size()>0){
            source.getIngredients().forEach(ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        return recipe;
    }
}
