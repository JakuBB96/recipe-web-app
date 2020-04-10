package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final String DESCRIPTION = "description";
    private static final Long VALUE = Long.valueOf(1L);
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(VALUE);
        unitOfMeasure.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(unitOfMeasure);

        assertEquals(VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());


    }
}