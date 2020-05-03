package com.barancewicz.recipewebapp.commands;

import com.barancewicz.recipewebapp.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    @NotBlank
    @Size(min =3, max = 255)
    private String description;
    @NotNull
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @NotNull
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @NotNull
    @Min(1)
    @Max(100)
    private Integer servings;
    @NotBlank
    private String source;
    @URL
    @NotBlank
    private String url;
    @NotBlank
    private String directions;
    @Valid
    private NotesCommand notes;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    @Valid
    private Set<CategoryCommand> categories = new HashSet<>();
    @NotNull
    private Difficulty difficulty;
    private UserCommand user;
}
