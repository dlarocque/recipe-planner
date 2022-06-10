package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;

import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Millilitre;

import org.junit.Test;

public class TestIngredient {
    @Test
    public void testTypicalValues() {
        String typicalName = "food";
        Millilitre typicalQuantity = new Millilitre(250);
        Millilitre newQuantity = new Millilitre(132.5);
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

        assertEquals(null, nullIngredient.getName());
        assertEquals(null, nullIngredient.getUnit());
    }

    @Test
    public void testEmptyString() {
        Ingredient emptyName = new Ingredient("", new Cup(2));

        assertEquals("", emptyName.getName());
    }

    @Test
    public void testZeroQuantity() {
        Gram empty = new Gram(0);
        Ingredient zeroQuantity = new Ingredient("nothing!", empty);

        assertEquals(empty, zeroQuantity.getUnit());
        assertEquals(0, zeroQuantity.getAmount(), 0);
    }

    @Test
    public void testNegativeQuantity() {
        Gram tapeworm = new Gram(-199);
        Ingredient zeroQuantity = new Ingredient("nothing!", tapeworm);

        assertEquals(tapeworm, zeroQuantity.getUnit());
        assertEquals(tapeworm.getAmount(), zeroQuantity.getAmount(), 0);
    }
}
