package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestTeaspoon {
    /** supported conversions and factors toMl = 5 toTbsp = 0.3333 toCup = 0.0208 */
    @Test
    public void testTypicalAmount() {
        double typical = 3;
        Teaspoon testTeaspoon = new Teaspoon(typical);

        assertEquals(typical, testTeaspoon.getAmount(), 0);
        assertEquals(15, testTeaspoon.convertTo(Unit.ML), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -1;
        Teaspoon testTeaspoon = new Teaspoon(negative);

        assertEquals(negative, testTeaspoon.getAmount(), 0);
        assertEquals(-0.3333, testTeaspoon.convertTo(Unit.TBSP), 0);
    }

    @Test
    public void testZeroAmount() {
        Teaspoon testTeaspoon = new Teaspoon(0);

        assertEquals(0, testTeaspoon.getAmount(), 0);
        assertEquals(0, testTeaspoon.convertTo(Unit.ML), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Teaspoon testTeaspoon = new Teaspoon(small);

        assertEquals(small, testTeaspoon.getAmount(), 0);
        assertEquals(0.000416, testTeaspoon.convertTo(Unit.CUP), 0);
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
