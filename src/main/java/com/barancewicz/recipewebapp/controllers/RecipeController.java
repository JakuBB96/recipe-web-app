package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.converters.CategoryToCategoryCommand;
import com.barancewicz.recipewebapp.converters.UserToUserCommand;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.services.CategoryService;
import com.barancewicz.recipewebapp.services.RecipeService;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String RECIPE_RECIPELIST_URL = "recipe/list";
    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserToUserCommand userToUserCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    @Autowired
    public void setCategoryToCategoryCommand(CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

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
            return "redirect:/recipes/find";
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
        return RECIPE_RECIPELIST_URL;
    }
    @GetMapping("/recipes/user/{username}")
    public String listUserRecipes(@PathVariable String username, Model model){
        model.addAttribute("recipes", recipeService.getUserRecipes(username));
        return RECIPE_RECIPELIST_URL;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
//        CommentCommand commentCommand = new CommentCommand();
        model.addAttribute("recipe", recipeCommand);
//        model.addAttribute("comment", commentCommand);
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id, @AuthenticationPrincipal UserDetails user){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes/user/" + user.getUsername();
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
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
                               BindingResult bindingResult,
                               @RequestParam(value = "cats" , required = false) Long[] cats,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        if (cats!=null){
            for (int i = 0; i < cats.length; i++) {
                command.getCategories().add(categoryToCategoryCommand.convert(categoryService.findById(cats[i])));
            }
        }
        command.setUser(userToUserCommand.convert(userService.findByUsername(userDetails.getUsername())));
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
