package com.barancewicz.recipewebapp.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UnitOfMeasure{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToOne
    private Ingredient ingredient;

    public UnitOfMeasure(String description) {
        this.description = description;
    }
}
