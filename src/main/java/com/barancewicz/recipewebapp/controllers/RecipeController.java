package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.services.CategoryService;
import com.barancewicz.recipewebapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model){
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipe/list";
    }

    @GetMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    //this method will render the view
    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("allCategories" , categoryService.getCategories());
        return RECIPE_RECIPEFORM_URL;
    }
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("allCategories" , categoryService.getCategories());
        return RECIPE_RECIPEFORM_URL;
    }
    //1. post mapping, refers to html document: th:action="@{/recipe/}"
    // when we get post to th:action="@{/recipe/}", we will invoke this method @RequestMapping("recipe")
    //2. @ModelAttribute this annotation tells spring to bind form attributes to the command object
    //3. We return savedCommand(service implementation) by saving passed command
    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() +"/show";
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("404error");

        return modelAndView;
    }
}
