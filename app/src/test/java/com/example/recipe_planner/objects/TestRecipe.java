package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Millilitre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestRecipe {
    @Test
    public void testTypicalNameInstructions() {
        String name = "secret cocktail";
        String instructions = "mix well, bring to a boil and enjoy! Serves 3.";
        ArrayList<Ingredient> list =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("chili peppers", new Cup(0.5)),
                                new Ingredient("cheddar", new Gram(400)),
                                new Ingredient("ketchup", new Cup(4))));
        Recipe typical = new Recipe(0, name, list, instructions);

        assertEquals(0, typical.getId());
        assertEquals(name, typical.getName());
        assertEquals(instructions, typical.getInstructions());
        assertFalse(typical.isDefault());
    }

    @Test
    public void testTypicalIngredients() {
        String name = "fun chunks";
        String instructions =
                "let marinate outdoors for 2 days, deep-fry and serve lukewarm. Serves 8.";
        ArrayList<Ingredient> list =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("McDonald's Chicken Nuggets", new Gram(400)),
                                new Ingredient("Pickle Juice", new Millilitre(250)),
                                new Ingredient("Gravy", new Cup(3.5))));
        Recipe typical = new Recipe(0, name, list, instructions, true);

        assertEquals(list, typical.getIngredients());

        Ingredient added = new Ingredient("shrimp", new Gram(300));
        list.add(added);
        typical.addIngredient(added);

        assertEquals(list, typical.getIngredients());
        assertTrue(typical.isDefault());
    }

    @Test
    public void testNullNameInstructions() {
        Recipe nullCake = new Recipe(0, null, null, null);

        assertNull(nullCake.getName());
        assertNull(nullCake.getInstructions());

        nullCake.setName(null);
        nullCake.setInstructions(null);

        assertNull(nullCake.getName());
        assertNull(nullCake.getInstructions());
        assertFalse(nullCake.isDefault());
    }

    @Test
    public void testNullIngredients() {
        Recipe nullCake = new Recipe(0, null, null, null);

        assertNull(nullCake.getIngredients());

        Ingredient nothing = new Ingredient(null, null);

        try {
            nullCake.addIngredient(nothing);
            fail("expected NullPointerException calling method of null ArrayList.");
        } catch (NullPointerException unused) {
            // all is well on this path
        }
    }

    @Test
    public void testEmptyNameInstructions() {
        Recipe empty = new Recipe(0, "", new ArrayList<>(), "", false);

        assertEquals("", empty.getName());
        assertEquals("", empty.getInstructions());

        empty.setName("");
        assertEquals("", empty.getName());

        empty.setInstructions("");
        assertEquals("", empty.getInstructions());

        assertFalse(empty.isDefault());
    }

    @Test
    public void testEmptyIngredients() {
        Recipe empty = new Recipe(0, "", new ArrayList<>(), "", true);

        assertEquals(0, empty.getIngredients().size());

        Ingredient added = new Ingredient("", new Gram(0));
        empty.addIngredient(added);

        assertEquals(1, empty.getIngredients().size());
        assertEquals(added, empty.getIngredients().get(0));

        assertTrue(empty.isDefault());
    }

    @Test
    public void testDefaultRecipe() {
        Recipe defaultRecipe = new Recipe(0, "", new ArrayList<>(), "", true);
        assertTrue(defaultRecipe.isDefault());
    }

    @Test
    public void testUserCreatedRecipe() {
        Recipe userCreatedRecipe = new Recipe(0, "", new ArrayList<>(), "", false);
        assertFalse(userCreatedRecipe.isDefault());

        userCreatedRecipe = new Recipe(0, "", new ArrayList<>(), "");
        assertFalse(userCreatedRecipe.isDefault());
    }
}
