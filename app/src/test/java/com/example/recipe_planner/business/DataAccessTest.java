package com.example.recipe_planner.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Unit;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DataAccessTest {

    private static final double QUARTER = 1.0 / 4.0;
    private static final double DELTA = 0.001;
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess");
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.getDBPathName());
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    @Test
    public void testGetDefaultRecipes() {
        List<Recipe> recipes;

        recipes = dataAccess.getRecipes();
        assertNotNull(recipes);
        assertEquals(4, recipes.size());

        Recipe recipe = recipes.get(0);
        assertNotNull(recipe);

        // check name
        assertEquals("Grilled Basil Chicken", recipe.getName());

        // check ingredients
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        assertEquals(5, ingredients.size());
        assertEquals(
                new Ingredient("Balsamic Vinegar", new ConvertibleUnit(Unit.CUP, 3 * QUARTER)),
                ingredients.get(0));
        assertEquals(
                new Ingredient("Basil Leaves", new ConvertibleUnit(Unit.CUP, QUARTER)),
                ingredients.get(1));
        assertEquals(
                new Ingredient("Olive Oil", new ConvertibleUnit(Unit.TBSP, 2)), ingredients.get(2));
        assertEquals(new Ingredient("Plum Tomatoes", new Count(4)), ingredients.get(3));
        assertEquals(
                new Ingredient("Boneless Skinless Chicken Breast", new Count(4)),
                ingredients.get(4));

        // check instructions
        String instructions =
                "After washing basil and tomatoes, blot them dry with clean paper towel.\n"
                        + "\n"
                        + "Using a clean cutting board, cut tomatoes into quarters.\n"
                        + "\n"
                        + "For marinade, place first six ingredients in a blender. Cover and process until well blended.\n"
                        + "\n"
                        + "Place chicken breasts in a shallow dish; orange do not rinse raw poultry. Cover with marinade. Cover dish. Refrigerate about 1 hour, turning occasionally. Wash dish after touching raw poultry.\n"
                        + "\n"
                        + "orange quote icon Wash hands with soap and water after handling uncooked chicken.\n"
                        + "\n"
                        + "Place chicken on an oiled grill rack over medium heat. Do not reuse marinades used on raw foods. Grill chicken 4-6 minutes per side. Cook until internal temperature reaches 165 °F as measured with a food thermometer. ";
        assertEquals(instructions, recipe.getInstructions());

        // check if default
        assertTrue(recipe.isDefault());
    }

    @Test
    public void testGetIngredientsFromInvalidRecipeIndex() {
        List<Ingredient> ingredients;

        ingredients = dataAccess.getRecipeIngredients(-1);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void testGetDefaultIngredients() {
        List<Ingredient> ingredients;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());

        // test all ingredients
        Ingredient ingredient = ingredients.get(0);
        assertNotNull(ingredient);
        assertEquals("Balsamic Vinegar", ingredient.getName());
        assertEquals(0.75, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(1);
        assertNotNull(ingredient);
        assertEquals("Basil Leaves", ingredient.getName());
        assertEquals(0.25, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(2);
        assertNotNull(ingredient);
        assertEquals("Olive Oil", ingredient.getName());
        assertEquals(2.0, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(3);
        assertNotNull(ingredient);
        assertEquals("Plum Tomatoes", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), DELTA);

        ingredient = ingredients.get(4);
        assertNotNull(ingredient);
        assertEquals("Boneless Skinless Chicken Breast", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), DELTA);
    }
}
