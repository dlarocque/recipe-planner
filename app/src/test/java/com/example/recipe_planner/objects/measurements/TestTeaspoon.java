package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTeaspoon {
    private static final double toTbsp = 0.3333333;
    private static final double toMl = 4.928906;
    private static final double toCup = 0.02083333;

    @Test
    public void testTypicalAmount() {
        double typical = 3;
        Teaspoon testTeaspoon = new Teaspoon(typical);

        assertEquals(typical, testTeaspoon.getAmount(), 0);
        assertEquals(typical, testTeaspoon.convertToTeaspoon(), 0);
        assertEquals(typical * toTbsp, testTeaspoon.convertToTablespoon(), 0);
        assertEquals(typical * toCup, testTeaspoon.convertToCup(), 0);
        assertEquals(typical * toMl, testTeaspoon.convertToMl(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -1;
        Teaspoon testTeaspoon = new Teaspoon(negative);

        assertEquals(negative, testTeaspoon.getAmount(), 0);
        assertEquals(negative, testTeaspoon.convertToTeaspoon(), 0);
        assertEquals(negative * toTbsp, testTeaspoon.convertToTablespoon(), 0);
        assertEquals(negative * toCup, testTeaspoon.convertToCup(), 0);
        assertEquals(negative * toMl, testTeaspoon.convertToMl(), 0);
    }

    @Test
    public void testZeroAmount() {
        Teaspoon testTeaspoon = new Teaspoon(0);

        assertEquals(0, testTeaspoon.getAmount(), 0);
        assertEquals(0, testTeaspoon.convertToTeaspoon(), 0);
        assertEquals(0, testTeaspoon.convertToTablespoon(), 0);
        assertEquals(0, testTeaspoon.convertToCup(), 0);
        assertEquals(0, testTeaspoon.convertToMl(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Teaspoon testTeaspoon = new Teaspoon(small);

        assertEquals(small, testTeaspoon.getAmount(), 0);
        assertEquals(small, testTeaspoon.convertToTeaspoon(), 0);
        assertEquals(small * toTbsp, testTeaspoon.convertToTablespoon(), 0);
        assertEquals(small * toCup, testTeaspoon.convertToCup(), 0);
        assertEquals(small * toMl, testTeaspoon.convertToMl(), 0);
    }
}
