package com.example.recipe_planner.objects.measurements;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestCup {

    @Test
    public void testTypicalAmount() {
        double typical = 3;
        Cup testCup = new Cup(typical);

        assertEquals(typical, testCup.getAmount(), 0);
        assertEquals(typical, testCup.convertToCup(), 0);
        assertEquals(typical * 16, testCup.convertToTablespoon(), 0);
        assertEquals(typical * 48, testCup.convertToTeaspoon(), 0);
        assertEquals(typical * 236.5875, testCup.convertToMl(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = 3;
        Cup testCup = new Cup(negative);

        assertEquals(negative, testCup.getAmount(), 0);
        assertEquals(negative, testCup.convertToCup(), 0);
        assertEquals(negative * 16, testCup.convertToTablespoon(), 0);
        assertEquals(negative * 48, testCup.convertToTeaspoon(), 0);
        assertEquals(negative * 236.5875, testCup.convertToMl(), 0);
    }

    @Test
    public void testZeroAmount() {
        double zero = 0;
        Cup testCup = new Cup(zero);

        assertEquals(zero, testCup.getAmount(), 0);
        assertEquals(zero, testCup.convertToCup(), 0);
        assertEquals(zero * 16, testCup.convertToTablespoon(), 0);
        assertEquals(zero * 48, testCup.convertToTeaspoon(), 0);
        assertEquals(zero * 236.5875, testCup.convertToMl(), 0);
    }
}
