package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestOunce {
    /** supported conversions and factors toGram = 0.0357 */
    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Ounce testOunce = new Ounce(typical);

        assertEquals(typical, testOunce.getAmount(), 0);
        assertEquals(0.1428, testOunce.convertTo(Unit.GRAM), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Ounce testOunce = new Ounce(negative);

        assertEquals(negative, testOunce.getAmount(), 0);
        assertEquals(-0.0714, testOunce.convertTo(Unit.GRAM), 0);
    }

    @Test
    public void testZeroAmount() {
        Ounce testOunce = new Ounce(0);

        assertEquals(0, testOunce.getAmount(), 0);
        assertEquals(0, testOunce.convertTo(Unit.GRAM), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Ounce testOunce = new Ounce(small);

        assertEquals(small, testOunce.getAmount(), 0);
        assertEquals(0.000714, testOunce.convertTo(Unit.GRAM), 0.0001);
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
