package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCup {
    private static final double toTbsp = 16;
    private static final double toTsp = 48;
    private static final double toMl = 236.5875;

    @Test
    public void testTypicalAmount() {
        double typical = 3;
        Cup testCup = new Cup(typical);

        assertEquals(typical, testCup.getAmount(), 0);
        assertEquals(typical, testCup.convertToCup(), 0);
        assertEquals(typical * toTbsp, testCup.convertToTablespoon(), 0);
        assertEquals(typical * toTsp, testCup.convertToTeaspoon(), 0);
        assertEquals(typical * toMl, testCup.convertToMl(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -1;
        Cup testCup = new Cup(negative);

        assertEquals(negative, testCup.getAmount(), 0);
        assertEquals(negative, testCup.convertToCup(), 0);
        assertEquals(negative * toTbsp, testCup.convertToTablespoon(), 0);
        assertEquals(negative * toTsp, testCup.convertToTeaspoon(), 0);
        assertEquals(negative * toMl, testCup.convertToMl(), 0);
    }

    @Test
    public void testZeroAmount() {
        Cup testCup = new Cup(0);

        assertEquals(0, testCup.getAmount(), 0);
        assertEquals(0, testCup.convertToCup(), 0);
        assertEquals(0, testCup.convertToTablespoon(), 0);
        assertEquals(0, testCup.convertToTeaspoon(), 0);
        assertEquals(0, testCup.convertToMl(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Cup testCup = new Cup(small);

        assertEquals(small, testCup.getAmount(), 0);
        assertEquals(small, testCup.convertToCup(), 0);
        assertEquals(small * toTbsp, testCup.convertToTablespoon(), 0);
        assertEquals(small * toTsp, testCup.convertToTeaspoon(), 0);
        assertEquals(small * toMl, testCup.convertToMl(), 0);
    }
}
