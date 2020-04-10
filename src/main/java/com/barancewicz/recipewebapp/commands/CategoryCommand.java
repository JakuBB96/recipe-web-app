package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String name;
    private Set<RecipeCommand> recipes = new HashSet<>();
}
