package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.IngredientCommand;
import com.barancewicz.recipewebapp.converters.IngredientCommandToIngredient;
import com.barancewicz.recipewebapp.converters.IngredientToIngredientCommand;
import com.barancewicz.recipewebapp.domain.Ingredient;
import com.barancewicz.recipewebapp.domain.Recipe;
import com.barancewicz.recipewebapp.repositories.RecipeRepository;
import com.barancewicz.recipewebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            log.error("Recipe not found id: " + recipeId);
            throw new RuntimeException("");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional =
                recipe.getIngredients()
                        .stream()//stream of ingredients
                        .filter(ingredient -> ingredient.getId().equals(ingredientId))//filter stream based on id of ingredient and passed id
                         .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();//when i get one, im mapping it to ingredientCommand object and - find first
        if (!ingredientCommandOptional.isPresent()){
            log.error("Ingredient not found id: " + recipeId);
            throw new RuntimeException("");
        }
        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long id) {
        log.debug("Deleteing ingredient: " + id);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe not found");
        }else{
            Optional<Ingredient> ingredientToDelete = recipeOptional.get().getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();
            if (ingredientToDelete.isPresent()){
                log.debug("found ingridient");
                Ingredient ingredient = ingredientToDelete.get();
                ingredient.setRecipe(null);
                recipeOptional.get().getIngredients().remove(ingredient);
                recipeRepository.save(recipeOptional.get());
            }
        }

    }
}
