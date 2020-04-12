package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.converters.RecipeCommandToRecipe;
import com.barancewicz.recipewebapp.converters.RecipeToRecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    public Set<Recipe> getRecipes(){
        log.debug("I'm on the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    public Recipe findById(Long id){
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
       if (!recipeOptional.isPresent()){
           throw new NotFoundException("Recipe not found. For id value: " + id);
       }
       return recipeOptional.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
       Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

       Recipe savedRecipe = recipeRepository.save(detachedRecipe);
       log.debug("Saved RecipeId: " + savedRecipe.getId());
       return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long id) {
//        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
//        if (!recipeOptional.isPresent()){
//            throw new RuntimeException("Recipe not found");
//        }
//        return recipeToRecipeCommand.convert(recipeOptional.get());
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
