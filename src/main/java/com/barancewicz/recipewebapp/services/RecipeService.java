package com.barancewicz.recipewebapp.services;


import com.barancewicz.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> findAll();
 }
