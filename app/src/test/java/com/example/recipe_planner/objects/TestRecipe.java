package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Ounce;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestRecipe {
    @Test
    public void testTypicalRecipe() {
        String name = "secret cocktail";
        String instructions = "mix well, bring to a boil and enjoy! Serves 3.";
        ArrayList<Ingredient> list =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("chili peppers", new Cup(0.5)),
                                new Ingredient("cheddar", new Gram(400)),
                                new Ingredient("ketchup", new Cup(4))));
        Recipe typical = new Recipe(name, list, instructions);

        assertEquals(name, typical.getName());
        assertEquals(list, typical.getIngredients());
        assertEquals(instructions, typical.getInstructions());

        Ingredient added = new Ingredient("chicken strips", new Gram(300));
        list.add(added);
        typical.addIngredient(added);

        assertEquals(list, typical.getIngredients());
    }

    @Test
    public void testDefineNameOnly() {
        Recipe onlyName = new Recipe("I have no details");

        assertEquals("I have no details", onlyName.getName());
        assertEquals(0, onlyName.getIngredients().size());
        assertNull(onlyName.getInstructions());

        Ingredient added = new Ingredient("bubblegum", new Ounce(20));
        onlyName.addIngredient(added);

        assertEquals(1, onlyName.getIngredients().size());
        assertEquals(added, onlyName.getIngredients().get(0));
    }

    @Test
    public void testNullValues() {
        Recipe nullCake = new Recipe(null, null, null);

        assertNull(nullCake.getName());
        assertNull(nullCake.getIngredients());
        assertNull(nullCake.getInstructions());

        Ingredient nothing = new Ingredient(null, null);

        try {
            nullCake.addIngredient(nothing);
            fail("expected NullPointerException calling method of null ArrayList.");
        } catch (NullPointerException unused) {
            // all is well on this path
        }
    }

    @Test
    public void testEmptyValues() {
        Recipe empty = new Recipe("", new ArrayList<>(), "");

        assertEquals("", empty.getName());
        assertEquals("", empty.getInstructions());
        assertEquals(0, empty.getIngredients().size());

        Ingredient added = new Ingredient("", new Gram(0));
        empty.addIngredient(added);

        assertEquals(1, empty.getIngredients().size());
        assertEquals(added, empty.getIngredients().get(0));
    }
}
