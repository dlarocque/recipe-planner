package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestGram {
    private static final double toOunce = 28.34952;

    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Gram testGram = new Gram(typical);

        assertEquals(typical, testGram.getAmount(), 0);
        assertEquals(typical, testGram.convertToGram(), 0);
        assertEquals(typical * toOunce, testGram.convertToOunce(), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Gram testGram = new Gram(negative);

        assertEquals(negative, testGram.getAmount(), 0);
        assertEquals(negative, testGram.convertToGram(), 0);
        assertEquals(negative * toOunce, testGram.convertToOunce(), 0);
    }

    @Test
    public void testZeroAmount() {
        Gram testGram = new Gram(0);

        assertEquals(0, testGram.getAmount(), 0);
        assertEquals(0, testGram.convertToGram(), 0);
        assertEquals(0, testGram.convertToOunce(), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Gram testGram = new Gram(small);

        assertEquals(small, testGram.getAmount(), 0);
        assertEquals(small, testGram.convertToGram(), 0);
        assertEquals(small * toOunce, testGram.convertToOunce(), 0);
    }
}
