package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.converters.UserToUserCommand;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.services.CategoryService;
import com.barancewicz.recipewebapp.services.RecipeService;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserToUserCommand userToUserCommand;

    public RecipeController(RecipeService recipeService, CategoryService categoryService, UserService userService, UserToUserCommand userToUserCommand) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.userToUserCommand = userToUserCommand;
    }
    @GetMapping("/recipes/find")
    public String findRecipes(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/find";
    }
    @GetMapping("/recipes/findRecipe")
    public String processFindForm(@ModelAttribute("recipe") RecipeCommand recipe, BindingResult result, Model model) {

        if (recipe.getDescription() == null) {
            recipe.setDescription(""); // empty string signifies broadest possible search
        }
        System.out.println(recipe.getDescription());
        List<RecipeCommand> results = recipeService.findAllByDescriptionLike("%"+recipe.getDescription()+"%");
        if (results.isEmpty()) {
            result.rejectValue("description", "notFound");
            return "/recipe/find";
        }
        else if (results.size() == 1) {
            recipe = results.get(0);
            return "redirect:/recipe/" + recipe.getId()+"/show";
        }
        else {
            model.addAttribute("selections", results);
            return "redirect:/recipes";
        }
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model){
        model.addAttribute("recipes", recipeService.getRecipesCommands());
        return "recipe/list";
    }
    @GetMapping("/recipes/user/{username}")
    public String listUserRecipes(@PathVariable String username, Model model){
        model.addAttribute("recipes", recipeService.getUserRecipes(username));
        return "recipe/list";
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }

    //this method will render the view
    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("allCategories" , categoryService.getCategories());
        return RECIPE_RECIPEFORM_URL;
    }
    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("allCategories" , categoryService.getCategories());
        return RECIPE_RECIPEFORM_URL;
    }
    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult, @AuthenticationPrincipal UserDetails user){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        command.setUser(userToUserCommand.convert(userService.findByUsername(user.getUsername())));
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        System.out.println(savedCommand.getUser().getUsername());
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
