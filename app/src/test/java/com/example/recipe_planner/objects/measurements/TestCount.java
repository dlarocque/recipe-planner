package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCount {
    @Test
    public void testTypicalAmount() {
        Count typical = new Count(2.5);

        assertEquals(2.5, typical.getAmount(), 0);
    }

    @Test
    public void testZeroAmount() {
        Count zero = new Count(0);

        assertEquals(0, zero.getAmount(), 0);
    }

    @Test
    public void testSmallFractionAmount() {
        Count smallFraction = new Count(0.003);

        assertEquals(0.003, smallFraction.getAmount(), 0);
    }

    @Test
    public void testNegativeAmount() {
        Count negative = new Count(-3);

        assertEquals(-3, negative.getAmount(), 0);
    }
}
