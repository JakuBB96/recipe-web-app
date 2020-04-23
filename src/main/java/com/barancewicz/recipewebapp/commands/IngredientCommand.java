package com.barancewicz.recipewebapp.commands;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    @NotBlank
    @Size(min =3, max = 255)
    private String description;
    @DecimalMin("0.01")
    @DecimalMax("100.00")
    private BigDecimal amount;
    @NotNull
    private UnitOfMeasureCommand uom;
    @NotNull
    private RecipeCommand recipe;
}
