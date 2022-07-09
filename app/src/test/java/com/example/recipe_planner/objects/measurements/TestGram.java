package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestGram {
    /** supported conversions and factors toOunce = 28 */
    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Gram testGram = new Gram(typical);

        assertEquals(typical, testGram.getAmount(), 0);
        assertEquals(112, testGram.convertTo(Unit.OUNCE), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Gram testGram = new Gram(negative);

        assertEquals(negative, testGram.getAmount(), 0);
        assertEquals(-56, testGram.convertTo(Unit.OUNCE), 0);
    }

    @Test
    public void testZeroAmount() {
        Gram testGram = new Gram(0);

        assertEquals(0, testGram.getAmount(), 0);
        assertEquals(0, testGram.convertTo(Unit.OUNCE), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Gram testGram = new Gram(small);

        assertEquals(small, testGram.getAmount(), 0);
        assertEquals(0.56, testGram.convertTo(Unit.OUNCE), 0);
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
