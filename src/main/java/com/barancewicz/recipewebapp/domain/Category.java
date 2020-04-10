package com.barancewicz.recipewebapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"recipes"})
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

}
