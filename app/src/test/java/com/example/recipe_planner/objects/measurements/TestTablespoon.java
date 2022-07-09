package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestTablespoon {
    /** supported conversions and factors toMl = 15 toTsp = 3 toCup = 0.0625 */
    @Test
    public void testTypicalAmount() {
        double typical = 4;
        Tablespoon testTablespoon = new Tablespoon(typical);

        assertEquals(typical, testTablespoon.getAmount(), 0);
        assertEquals(12, testTablespoon.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testNegativeAmount() {
        double negative = -2;
        Tablespoon testTablespoon = new Tablespoon(negative);

        assertEquals(negative, testTablespoon.getAmount(), 0);
        assertEquals(-0.125, testTablespoon.convertTo(Unit.CUP), 0);
    }

    @Test
    public void testZeroAmount() {
        Tablespoon testTablespoon = new Tablespoon(0);

        assertEquals(0, testTablespoon.getAmount(), 0);
        assertEquals(0, testTablespoon.convertTo(Unit.ML), 0);
    }

    @Test
    public void testSmallFraction() {
        double small = 0.02;
        Tablespoon testTablespoon = new Tablespoon(small);

        assertEquals(small, testTablespoon.getAmount(), 0);
        assertEquals(0.3, testTablespoon.convertTo(Unit.ML), 0);
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
