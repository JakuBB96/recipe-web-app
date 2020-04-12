package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.UnitOfMeasure;
import com.barancewicz.recipewebapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        // 1. find all, 2. spliterator...3. map to convert domain to commands and 4. collect them int oa set and return
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());

    }
}
