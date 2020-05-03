package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.converters.UserToUserCommand;
import com.barancewicz.recipewebapp.exceptions.NotFoundException;
import com.barancewicz.recipewebapp.services.CategoryService;
import com.barancewicz.recipewebapp.services.RecipeService;
import com.barancewicz.recipewebapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    CategoryService categoryService;
    @Mock
    UserService userService;
    @Mock
    UserToUserCommand userToUserCommand;
    RecipeController controller;
    @Mock
    Model model;
    @Mock
    UserDetailsService userDetailsService;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService, categoryService, userService, userToUserCommand);
        mockMvc = mockMvc = MockMvcBuilders.standaloneSetup(controller)
                    .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void processFindForm() throws Exception {
        List<RecipeCommand> recipeCommandList = new ArrayList<>();
        recipeCommandList.add(new RecipeCommand());
        recipeCommandList.add(new RecipeCommand());
        recipeCommandList.add(new RecipeCommand());
        when(recipeService.findAllByDescriptionLike(anyString()))
                .thenReturn(recipeCommandList);

        mockMvc.perform(get("/recipes/findRecipe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"))
                .andExpect(model().attribute("selections", hasSize(3)));

    }

    @Test
    public void getUserRecipes() throws Exception {
        Set<RecipeCommand> recipes = new HashSet<>();
        recipes.add(new RecipeCommand());
        recipes.add(new RecipeCommand());
        recipes.add(new RecipeCommand());

        when(recipeService.getUserRecipes(anyString())).thenReturn(recipes);
        mockMvc.perform(get("/recipes/user/somename"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/list"))
                .andExpect(model().attribute("recipes", hasSize(3)));
        verify(recipeService, times(1)).getUserRecipes(anyString());
    }

    @Test
    public void findRecipe() throws Exception{
        String viewName = controller.findRecipes(model);
        assertEquals("recipe/find", viewName);
    }

    @Test
    public void testGetRecipe() throws Exception {
        RecipeCommand r1 = new RecipeCommand();
        r1.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(r1);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/show"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception{

        when(recipeService.findCommandById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void testGetRecipeNumberFormatE() throws Exception{

//        when(recipeService.findById(any())).thenThrow(NumberFormatException.class);
        mockMvc.perform(get("/recipe/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
       mockMvc.perform(get("/recipe/new"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("recipe"))
               .andExpect(view().name("recipe/recipeform"));
    }

//    @Test
//    public void testPostNewRecipeForm() throws Exception {
//        RecipeCommand command = new RecipeCommand();
//        command.setId(2L);
//        when(recipeService.saveRecipeCommand(any())).thenReturn(command);
//
//        mockMvc.perform(post("/recipe")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "")
//                .param("description", "some string")
//                .param("directions", "some directionss")
//                .param("url", "http://example.com")
//                .param("prepTime", "2")
//                .param("cookTime", "2")
//                .param("servings", "2")
//                .param("notes", "notes")
//                .param("source", "http://www.example.org")
//                .param("difficulty", "EASY")
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/recipe/2/show"));
//    }
//
//    @Test
//    public void testPostNewRecipeFormValidationFail() throws Exception {
//        RecipeCommand command = new RecipeCommand();
//        command.setId(2L);
//
//        when(recipeService.saveRecipeCommand(any())).thenReturn(command);
//
//        mockMvc.perform(post("/recipe")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "")
//                .param("cookTime", "3000")
//        )
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("recipe"))
//                .andExpect(view().name("recipe/recipeform"));
//    }


    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

}