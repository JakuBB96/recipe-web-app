package com.barancewicz.recipewebapp.converters;

import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String DESCRIPTION = "description";
    private static final Long VALUE = Long.valueOf(1L);
    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {

        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setDescription(DESCRIPTION);
        command.setId(VALUE);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(command);

        assertEquals(VALUE, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());

    }
}