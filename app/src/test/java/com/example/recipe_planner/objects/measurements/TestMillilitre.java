package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestMillilitre {
    /** supported conversions and factors toTbsp = 0.0667 toTsp = 0.2 toCup = 0.0042 */
    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Millilitre testMillilitre = new Millilitre(typical);

        assertEquals(typical, testMillilitre.getAmount(), 0);
        assertEquals(0.2668, testMillilitre.convertTo(Unit.TBSP), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Millilitre testMillilitre = new Millilitre(negative);

        assertEquals(negative, testMillilitre.getAmount(), 0);
        assertEquals(-0.0084, testMillilitre.convertTo(Unit.CUP), 0);
    }

    @Test
    public void testZeroAmount() {
        Millilitre testMillilitre = new Millilitre(0);

        assertEquals(0, testMillilitre.getAmount(), 0);
        assertEquals(0, testMillilitre.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Millilitre testMillilitre = new Millilitre(small);

        assertEquals(small, testMillilitre.getAmount(), 0);
        assertEquals(0.000084, testMillilitre.convertTo(Unit.CUP), 0);
    }

    @Test
    public void testInvalidConversion() {
        double amount = 2.5;
        Gram testGram = new Gram(amount);

        try {
            testGram.convertTo(null);
            fail("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }
}
