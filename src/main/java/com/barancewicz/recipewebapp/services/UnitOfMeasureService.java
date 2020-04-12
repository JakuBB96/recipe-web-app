package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
