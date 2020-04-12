package com.barancewicz.recipewebapp.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Recipe{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    //1t1 mapping, by cascasde all recipe is the owner
    //if we delete recipe, we will also delete notes
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @Lob
    private Byte[] image;
    //mappedBy: property on the child class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        return this;
    }
}
