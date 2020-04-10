package com.barancewicz.recipewebapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    private Long id = 1L;
    private String name = "FastFood";
    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
        category.setId(id);
        category.setName(name);
    }

    @Test
    public void getId() {
        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
        assertEquals(name, category.getName());
    }

    @Test
    public void getRecipes() {
        assertEquals(0, category.getRecipes().size());
    }
}