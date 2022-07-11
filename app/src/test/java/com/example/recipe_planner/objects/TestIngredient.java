package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Unit;

import org.junit.Test;

public class TestIngredient {
    @Test
    public void testTypicalValues() {
        String typicalName = "food";
        ConvertibleUnit typicalQuantity = new ConvertibleUnit(Unit.ML, 250);
        ConvertibleUnit newQuantity = new ConvertibleUnit(Unit.OUNCE, 132.5);
        Ingredient typicalIngredient = new Ingredient(typicalName, typicalQuantity);

        assertEquals(typicalName, typicalIngredient.getName());
        assertEquals(typicalQuantity.getAmount(), typicalIngredient.getAmount(), 0);
        assertEquals(typicalQuantity, typicalIngredient.getUnit());

        typicalIngredient.setAmount(newQuantity);

        assertEquals(newQuantity, typicalIngredient.getUnit());
    }

    @Test
    public void testNullValues() {
        Ingredient nullIngredient = new Ingredient(null, null);

        assertNull(nullIngredient.getName());
        assertNull(nullIngredient.getUnit());
    }

    @Test
    public void testEmptyString() {
        Ingredient emptyName = new Ingredient("", new Count(2));

        assertEquals("", emptyName.getName());
    }

    @Test
    public void testZeroQuantity() {
        ConvertibleUnit empty = new ConvertibleUnit(Unit.GRAM, 0);
        Ingredient zeroQuantity = new Ingredient("nothing!", empty);

        assertEquals(empty, zeroQuantity.getUnit());
        assertEquals(0, zeroQuantity.getAmount(), 0);
    }

    @Test
    public void testNegativeQuantity() {
        ConvertibleUnit tapeworm = new ConvertibleUnit(Unit.GRAM, -199);
        Ingredient zeroQuantity = new Ingredient("nothing!", tapeworm);

        assertEquals(tapeworm, zeroQuantity.getUnit());
        assertEquals(tapeworm.getAmount(), zeroQuantity.getAmount(), 0);
    }
}
