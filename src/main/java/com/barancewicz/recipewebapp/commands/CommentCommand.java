package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCommand {
    private Long id;
    private RecipeCommand recipe;
    private UserCommand user;
    private String text;
}
