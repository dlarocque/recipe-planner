package com.example.recipe_planner.objects.measurements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestConvertibleUnit {
    @Test
    public void testTypicalCup() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.CUP, 2);

        assertEquals(2, typical.getAmount(), 0);
        assertEquals("2.0 CUP", typical.toString());
        assertEquals(Unit.CUP, typical.getUnit());
        assertEquals(474, typical.convertTo(Unit.ML), 0);

        typical = new ConvertibleUnit(Unit.CUP, 2);
        assertEquals(32, typical.convertTo(Unit.TBSP), 0);

        typical = new ConvertibleUnit(Unit.CUP, 2);
        assertEquals(96, typical.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testTypicalTeaspoon() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.TSP, 120);

        assertEquals(120, typical.getAmount(), 0);
        assertEquals("120.0 TSP", typical.toString());
        assertEquals(Unit.TSP, typical.getUnit());
        assertEquals(40, typical.convertTo(Unit.TBSP), 0.01);

        typical = new ConvertibleUnit(Unit.TSP, 120);
        assertEquals(600, typical.convertTo(Unit.ML), 0);

        typical = new ConvertibleUnit(Unit.TSP, 120);
        assertEquals(2.496, typical.convertTo(Unit.CUP), 0);
    }

    @Test
    public void testTypicalTablespoon() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.TBSP, 4);

        assertEquals(4, typical.getAmount(), 0);
        assertEquals("4.0 TBSP", typical.toString());
        assertEquals(Unit.TBSP, typical.getUnit());
        assertEquals(0.25, typical.convertTo(Unit.CUP), 0);

        typical = new ConvertibleUnit(Unit.TBSP, 4);
        assertEquals(60, typical.convertTo(Unit.ML), 0);

        typical = new ConvertibleUnit(Unit.TBSP, 4);
        assertEquals(12, typical.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testTypicalMillilitre() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.ML, 200);

        assertEquals(200, typical.getAmount(), 0);
        assertEquals("200.0 ML", typical.toString());
        assertEquals(Unit.ML, typical.getUnit());
        assertEquals(40, typical.convertTo(Unit.TSP), 0);

        typical = new ConvertibleUnit(Unit.ML, 200);
        assertEquals(13.34, typical.convertTo(Unit.TBSP), 0);

        typical = new ConvertibleUnit(Unit.ML, 200);
        assertEquals(0.84, typical.convertTo(Unit.CUP), 0);
    }

    @Test
    public void testTypicalGram() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.GRAM, 350);

        assertEquals(350, typical.getAmount(), 0);
        assertEquals("350.0 GRAM", typical.toString());
        assertEquals(Unit.GRAM, typical.getUnit());
        assertEquals(12.495, typical.convertTo(Unit.OUNCE), 0.01);
    }

    @Test
    public void testTypicalOunce() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.OUNCE, 12);

        assertEquals(12, typical.getAmount(), 0);
        assertEquals("12.0 OUNCE", typical.toString());
        assertEquals(Unit.OUNCE, typical.getUnit());
        assertEquals(336, typical.convertTo(Unit.GRAM), 0);
    }

    @Test
    public void testNegativeMeasurement() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.TBSP, -12);

        assertEquals(-12, typical.getAmount(), 0);
        assertEquals(-36, typical.convertTo(Unit.TSP), 0);
    }

    @Test
    public void testZeroMeasurement() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.OUNCE, 0);

        assertEquals(0, typical.getAmount(), 0);
        assertEquals(0, typical.convertTo(Unit.GRAM), 0);
    }

    @Test
    public void testSmallFractionMeasurement() {
        ConvertibleUnit typical = new ConvertibleUnit(Unit.CUP, 0.1);

        assertEquals(0.1, typical.getAmount(), 0);
        assertEquals(23.7, typical.convertTo(Unit.ML), 0.01);
    }

    @Test
    public void testConvertBackToOriginalUnit() {
        ConvertibleUnit switcharoo = new ConvertibleUnit(Unit.TBSP, 10);

        assertEquals(150, switcharoo.convertTo(Unit.ML), 0);
        assertEquals(Unit.ML, switcharoo.getUnit());
        assertEquals(10, switcharoo.convertTo(Unit.TBSP), 0.01);
        assertEquals(Unit.TBSP, switcharoo.getUnit());
    }

    @Test
    public void testInvalidConversion() {
        ConvertibleUnit test = new ConvertibleUnit(Unit.GRAM, 230);

        // conversions between volumes and masses are unsupported
        try {
            test.convertTo(Unit.ML);
            fail("Expected an UnsupportedOperationException here");
        } catch (UnsupportedOperationException ignore) {
        }

        test = new ConvertibleUnit(Unit.CUP, 4);
        try {
            test.convertTo(Unit.OUNCE);
            fail("Expected an UnsupportedOperationException here");
        } catch (UnsupportedOperationException ignore) {
        }
    }

    @Test
    public void testNullUnit() {
        ConvertibleUnit test = new ConvertibleUnit(null, 20);

        // convert from null unit to valid unit
        assertNull(test.getUnit());
        try {
            test.convertTo(Unit.GRAM);
            fail("Expected an UnsupportedOperationException here");
        } catch (UnsupportedOperationException ignore) {
        }

        // convert from valid unit to null unit
        ConvertibleUnit convertToNull = new ConvertibleUnit(Unit.OUNCE, 12);
        try {
            convertToNull.convertTo(null);
            fail("Expected an UnsupportedOperationException here");
        } catch (UnsupportedOperationException ignore) {
        }
    }
}
