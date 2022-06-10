package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMillilitre {
    private static final double toTbsp = 0.06762826;
    private static final double toTsp = 0.2028848;
    private static final double toCup = 0.004226766249;

    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Millilitre testMillilitre = new Millilitre(typical);

        assertEquals(typical, testMillilitre.getAmount(), 0);
        assertEquals(typical, testMillilitre.convertToMl(), 0);
        assertEquals(typical * toTsp, testMillilitre.convertToTeaspoon(), 0);
        assertEquals(typical * toCup, testMillilitre.convertToCup(), 0);
        assertEquals(typical * toTbsp, testMillilitre.convertToTablespoon(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Millilitre testMillilitre = new Millilitre(negative);

        assertEquals(negative, testMillilitre.getAmount(), 0);
        assertEquals(negative, testMillilitre.convertToMl(), 0);
        assertEquals(negative * toTsp, testMillilitre.convertToTeaspoon(), 0);
        assertEquals(negative * toCup, testMillilitre.convertToCup(), 0);
        assertEquals(negative * toTbsp, testMillilitre.convertToTablespoon(), 0);
    }

    @Test
    public void testZeroAmount() {
        Millilitre testMillilitre = new Millilitre(0);

        assertEquals(0, testMillilitre.getAmount(), 0);
        assertEquals(0, testMillilitre.convertToTablespoon(), 0);
        assertEquals(0, testMillilitre.convertToTeaspoon(), 0);
        assertEquals(0, testMillilitre.convertToCup(), 0);
        assertEquals(0, testMillilitre.convertToMl(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Tablespoon testMillilitre = new Tablespoon(small);

        assertEquals(small, testMillilitre.getAmount(), 0);
        assertEquals(small, testMillilitre.convertToMl(), 0);
        assertEquals(small * toTsp, testMillilitre.convertToTeaspoon(), 0);
        assertEquals(small * toCup, testMillilitre.convertToCup(), 0);
        assertEquals(small * toTbsp, testMillilitre.convertToTablespoon(), 0);
    }
}
