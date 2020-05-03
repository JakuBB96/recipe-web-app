package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.CategoryCommand;
import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.Category;

import java.util.Set;

public interface CategoryService{
    Set<CategoryCommand> getCategories();
    Category findById (Long id);
    CategoryCommand findCategoryById(Long id);
    void deleteById(Long id);
    CategoryCommand saveCategory(CategoryCommand command);
}
