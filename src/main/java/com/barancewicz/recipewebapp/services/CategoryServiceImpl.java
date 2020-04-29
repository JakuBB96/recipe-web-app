package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.CategoryCommand;
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
    private final CategoryToCategoryCommand converter;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand converter) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
    }

    @Override
    public Set<CategoryCommand> getCategories() {
        log.debug("Retrieving categories from database");
        Set<CategoryCommand> categoryCommands = new HashSet<>();
        categoryRepository.findAll()
                            .forEach(category -> categoryCommands.add(
                                    converter.convert(category)
                            ));
        return categoryCommands;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }
}
