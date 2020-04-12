package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.IngredientCommand;
import com.barancewicz.recipewebapp.converters.IngredientCommandToIngredient;
import com.barancewicz.recipewebapp.converters.IngredientToIngredientCommand;
import com.barancewicz.recipewebapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.barancewicz.recipewebapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.Ingredient;
import com.barancewicz.recipewebapp.domain.Recipe;
import com.barancewicz.recipewebapp.repositories.RecipeRepository;
import com.barancewicz.recipewebapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(
                recipeRepository,
                ingredientToIngredientCommand,
                unitOfMeasureRepository,
                ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());


    }

    @Test
    public void deleteIngredientTest(){

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ingredientService.deleteById(recipe.getId(), ingredient.getId());

        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);


        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}