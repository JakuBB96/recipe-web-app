package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService{
    Set<UnitOfMeasureCommand> listAllUoms();
    UnitOfMeasureCommand findUomById(Long id);
    UnitOfMeasureCommand saveUOM(UnitOfMeasureCommand command);
    UnitOfMeasure saveUOM(UnitOfMeasure uom);
}
