package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTablespoon {
    private static final double toTsp = 3;
    private static final double toMl = 14.78672;
    private static final double toCup = 0.0625;

    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Tablespoon testTablespoon = new Tablespoon(typical);

        assertEquals(typical, testTablespoon.getAmount(), 0);
        assertEquals(typical, testTablespoon.convertToTablespoon(), 0);
        assertEquals(typical * toTsp, testTablespoon.convertToTeaspoon(), 0);
        assertEquals(typical * toCup, testTablespoon.convertToCup(), 0);
        assertEquals(typical * toMl, testTablespoon.convertToMl(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Tablespoon testTablespoon = new Tablespoon(negative);

        assertEquals(negative, testTablespoon.getAmount(), 0);
        assertEquals(negative, testTablespoon.convertToTablespoon(), 0);
        assertEquals(negative * toTsp, testTablespoon.convertToTeaspoon(), 0);
        assertEquals(negative * toCup, testTablespoon.convertToCup(), 0);
        assertEquals(negative * toMl, testTablespoon.convertToMl(), 0);
    }

    @Test
    public void testZeroAmount() {
        Tablespoon testTablespoon = new Tablespoon(0);

        assertEquals(0, testTablespoon.getAmount(), 0);
        assertEquals(0, testTablespoon.convertToTablespoon(), 0);
        assertEquals(0, testTablespoon.convertToTeaspoon(), 0);
        assertEquals(0, testTablespoon.convertToCup(), 0);
        assertEquals(0, testTablespoon.convertToMl(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Tablespoon testTablespoon = new Tablespoon(small);

        assertEquals(small, testTablespoon.getAmount(), 0);
        assertEquals(small, testTablespoon.convertToTablespoon(), 0);
        assertEquals(small * toTsp, testTablespoon.convertToTeaspoon(), 0);
        assertEquals(small * toCup, testTablespoon.convertToCup(), 0);
        assertEquals(small * toMl, testTablespoon.convertToMl(), 0);
    }
}
