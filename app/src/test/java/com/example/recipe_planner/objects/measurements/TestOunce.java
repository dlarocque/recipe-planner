package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestOunce {
    private static final double toGram = 0.03527396;

    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Ounce testOunce = new Ounce(typical);

        assertEquals(typical, testOunce.getAmount(), 0);
        assertEquals(typical, testOunce.convertToOunce(), 0);
        assertEquals(typical * toGram, testOunce.convertToGram(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Ounce testOunce = new Ounce(negative);

        assertEquals(negative, testOunce.getAmount(), 0);
        assertEquals(negative, testOunce.convertToOunce(), 0);
        assertEquals(negative * toGram, testOunce.convertToGram(), 0);
    }

    @Test
    public void testZeroAmount() {
        Ounce testOunce = new Ounce(0);

        assertEquals(0, testOunce.getAmount(), 0);
        assertEquals(0, testOunce.convertToOunce(), 0);
        assertEquals(0, testOunce.convertToGram(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Ounce testOunce = new Ounce(small);

        assertEquals(small, testOunce.getAmount(), 0);
        assertEquals(small, testOunce.convertToOunce(), 0);
        assertEquals(small * toGram, testOunce.convertToGram(), 0);
    }
}
