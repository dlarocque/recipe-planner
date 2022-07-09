package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestCup {
    /** supported conversions and factors toTbsp = 16; toTsp = 48; toMl = 237; */
    @Test
    public void testTypicalAmount() {
        double typical = 3;
        Cup testCup = new Cup(typical);

        assertEquals(typical, testCup.getAmount(), 0);
        assertEquals(48, testCup.convertTo(Unit.TBSP), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -1;
        Cup testCup = new Cup(negative);

        assertEquals(negative, testCup.getAmount(), 0);
        assertEquals(-48, testCup.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testZeroAmount() {
        Cup testCup = new Cup(0);

        assertEquals(0, testCup.getAmount(), 0);
        assertEquals(0, testCup.convertTo(Unit.ML), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Cup testCup = new Cup(small);

        assertEquals(small, testCup.getAmount(), 0);
        assertEquals(0.96, testCup.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testInvalidConversion() {
        double amount = 2.5;
        Cup testCup = new Cup(amount);

        try {
            testCup.convertTo(null);
            fail("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }
}
