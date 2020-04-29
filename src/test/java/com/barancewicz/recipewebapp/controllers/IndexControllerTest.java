package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.RecipeCommand;
import com.barancewicz.recipewebapp.domain.Recipe;
import com.barancewicz.recipewebapp.services.RecipeService;
import com.barancewicz.recipewebapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {


    @Mock
    RecipeService recipeService;
    @Mock
    UserService userService;
    @Mock
    Model model;

    private IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService, userService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {

        //given
        Set<RecipeCommand> recipes = new HashSet<>();
        recipes.add(new RecipeCommand());
        RecipeCommand r1 = new RecipeCommand();
        r1.setId(1L);
        r1.setDescription("Test");
        recipes.add(r1);

        when(recipeService.getRecipesCommands()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getIndexPage(model);
        assertEquals("index", viewName);

        //then
        verify(recipeService, times(1)).getRecipesCommands();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();

        assertEquals(2, setInController.size());
    }
}