package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.CategoryCommand;
import com.barancewicz.recipewebapp.converters.CategoryCommandToCategory;
import com.barancewicz.recipewebapp.converters.CategoryToCategoryCommand;
import com.barancewicz.recipewebapp.domain.Category;
import com.barancewicz.recipewebapp.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final CategoryCommandToCategory categoryCommandToCategory;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand, CategoryCommandToCategory categoryCommandToCategory) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Override
    public Set<CategoryCommand> getCategories() {
        log.debug("Retrieving categories from database");
        Set<CategoryCommand> categoryCommands = new HashSet<>();
        categoryRepository.findAll()
                            .forEach(category -> categoryCommands.add(
                                    categoryToCategoryCommand.convert(category)
                            ));
        return categoryCommands;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public CategoryCommand findCategoryById(Long id) {
        return categoryToCategoryCommand.convert(categoryRepository.findById(id).get());
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.getRecipes().forEach(recipe -> recipe.getCategories().remove(category));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryCommand saveCategory(CategoryCommand command) {
        Category detachedCat = categoryCommandToCategory.convert(command);
        return categoryToCategoryCommand.convert(categoryRepository.save(detachedCat));
    }
}
