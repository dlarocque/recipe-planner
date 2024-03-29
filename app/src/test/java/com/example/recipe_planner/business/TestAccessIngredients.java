package com.example.recipe_planner.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestAccessIngredients {
    private static final double DELTA = 0.001;
    private DataAccess dataAccess;
    private AccessIngredients accessIngredients;

    @Before
    public void setUp() {
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
        Services.createDataAccess(dataAccess);
        accessIngredients = new AccessIngredients();
    }

    @After
    public void tearDown() {
        dataAccess.close();
    }

    @Test
    public void TestDeletingExistingIngredient() {

        // getting existing ingredients from recipe 0
        List<Ingredient> ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());
        Ingredient ingredient = ingredients.get(0);

        // Ingredients queried with recipe ID should be the same as those in the recipe
        String unit = ingredients.get(0).getUnit().getClass().getSimpleName();

        accessIngredients.deleteIngredient(0, ingredient.getName());
        ingredients = dataAccess.getRecipeIngredients(0);
        assertEquals("Basil Leaves", ingredients.get(0).getName());
        assertEquals(4, ingredients.size());

        for (int i = 0; i < ingredients.size(); i++) {
            assert !ingredients.get(i).getName().equals("Balsamic Vinegar");
        }
    }

    @Test
    public void TestDeletingNonExistingIngredient() {

        // getting existing ingredients from recipe 0
        List<Ingredient> ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());
        Ingredient ingredient = ingredients.get(0);

        // Ingredients queried with recipe ID should be the same as those in the recipe
        String unit = ingredients.get(0).getUnit().getClass().getSimpleName();

        accessIngredients.deleteIngredient(0, "Cinnamon");
        ingredients = dataAccess.getRecipeIngredients(0);
        assertEquals("Balsamic Vinegar", ingredients.get(0).getName());
        assertEquals(5, ingredients.size());
    }

    @Test
    public void TestModifyingExistingIngredient() {
        List<Ingredient> ingredients = dataAccess.getRecipeIngredients(0);

        assertEquals(0.25, ingredients.get(1).getAmount(), DELTA);

        accessIngredients.updateIngredientQuantity(0, 50.0, "Basil Leaves");
        ingredients = dataAccess.getRecipeIngredients(0);

        assertEquals(50.0, ingredients.get(1).getAmount(), DELTA);

        accessIngredients.updateIngredientName(0, "Oregano", "Basil Leaves");
        ingredients = dataAccess.getRecipeIngredients(0);

        assertEquals(5, ingredients.size());
        assertEquals(
                "Oregano",
                ingredients
                        .get(4)
                        .getName()); // the ingredient is at the end of the list because it's "new"

        for (int i = 0; i < ingredients.size(); i++) {
            assert !ingredients.get(i).getName().equals("Basil Leaves");
        }
    }

    @Test
    public void TestModifyingNonExistingIngredient() {

        // getting existing ingredients from recipe 0
        List<Ingredient> ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);
        assertEquals(5, ingredients.size());
        Ingredient ingredient = ingredients.get(0);

        // Ingredients queried with recipe ID should be the same as those in the recipe
        String unit = ingredients.get(0).getUnit().getClass().getSimpleName();

        accessIngredients.updateIngredientQuantity(0, 9.0, "Cinnamon");
        ingredients = dataAccess.getRecipeIngredients(0);
        assertEquals("Basil Leaves", ingredients.get(1).getName());
        assertEquals(2.0, ingredients.get(2).getAmount(), DELTA);
        assertEquals(5, ingredients.size());

        accessIngredients.updateIngredientName(0, "Red Peppers", "Cinnamon");
        ingredients = dataAccess.getRecipeIngredients(0);

        assertEquals(5, ingredients.size());

        for (int i = 0; i < ingredients.size(); i++) {
            assert !ingredients.get(i).getName().equals("Cinnamon");
        }
    }
}
