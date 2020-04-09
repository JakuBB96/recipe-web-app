package com.barancewicz.recipewebapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class UnitOfMeasure extends BaseEntity{
    private String description;
    @OneToOne
    private Ingredient ingredient;
}
