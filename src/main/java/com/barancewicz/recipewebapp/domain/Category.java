package com.barancewicz.recipewebapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"recipes"})
public class Category extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
