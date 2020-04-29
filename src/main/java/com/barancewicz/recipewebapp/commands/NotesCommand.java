package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private RecipeCommand recipe;
    @NotBlank
    private String recipeNotes;

}
