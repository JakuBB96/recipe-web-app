package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.converters.RecipeCommandToRecipe;
import com.barancewicz.recipewebapp.converters.RecipeToRecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final UserService userService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.userService = userService;
    }

    public Set<Recipe> getRecipes(){
        log.debug("I'm on the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Set<RecipeCommand> getRecipesCommands() {
        return getRecipes()
                .stream()
                .map(recipe -> recipeToRecipeCommand.convert(recipe))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RecipeCommand> getUserRecipes(String username) {
        return getRecipes()
                .stream()
                .filter(recipe -> recipe.getUser().getUsername().equals(username))
                .map(recipe -> recipeToRecipeCommand.convert(recipe))
                .collect(Collectors.toSet());
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
       if (detachedRecipe.getImage()!=null||command.getId()!=null){
           detachedRecipe.setImage(findById(command.getId()).getImage());
       }
        System.out.println(detachedRecipe.getUser());
        User toBeUpdated = userService.findByUsername(detachedRecipe.getUser().getUsername());
        toBeUpdated.addRecipe(detachedRecipe);
        userService.saveOrUpdate(toBeUpdated);

     return getRecipesCommands()
              .stream()
              .filter(command1 -> command1.getDescription().equals(command.getDescription()))
              .findFirst()
             .orElseThrow(() -> new NotFoundException());
      // Recipe savedRecipe = recipeRepository.save(detachedRecipe);
      // log.debug("Saved RecipeId: " + savedRecipe.getId());
    }

    @Override
    public List<RecipeCommand> findAllByDescriptionLike(String description) {
        return recipeRepository.findAllByDescriptionLike(description)
                .stream()
                .map(recipe -> recipeToRecipeCommand.convert(recipe))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(Long id) throws NotFoundException {
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
