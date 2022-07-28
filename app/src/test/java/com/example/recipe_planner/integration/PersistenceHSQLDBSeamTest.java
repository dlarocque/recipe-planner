package com.example.recipe_planner.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Unit;
import com.example.recipe_planner.persistence.DataAccess;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersistenceHSQLDBSeamTest {
    private static final String testDbName = "database/RecipesTest";
    private final double DELTA = 0.001;
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        Services.createDataAccess(testDbName); // HSQLDB
        dataAccess = Services.getDataAccess();
        dataAccess.reset();
    }

    @BeforeClass
    public static void printStart() {
        System.out.println("Running integration tests: persistence/HSQLDB seam  [using HSQLDB]");
    }

    @AfterClass
    public static void printEnd() {
        System.out.println("Finished persistence/HSQLDB seam tests  [using HSQLDB]");
    }

    @Test
    public void accessOpenDatabaseTest() {
        // The database in dataAccess is already open
        // Check that the dataAccess exists and we can make calls to HSQLDB
        assertNotNull(dataAccess);

        // Queries aren't null
        assertNotNull(dataAccess.getRecipes());

        // Modifications are true
        assertTrue(dataAccess.deleteRecipe(0));
        assertTrue(dataAccess.deleteIngredient(0, "Water", 0.75, Unit.CUP.name()));
    }

    @Test
    public void accessClosedDatabaseTest() {
        dataAccess.close();

        // All queries return null
        assertNull(dataAccess.getRecipes());
        assertNull(dataAccess.getRecipe(0));
        assertNull(dataAccess.getRecipeIngredients(0));

        // Deletions do not occur
        assertFalse(dataAccess.deleteRecipe(0));
        assertFalse(dataAccess.deleteIngredient(0, "Water", 0.75, Unit.CUP.name()));

        // Deletions that occurs when database was closed did not complete
        dataAccess.open(testDbName);
        Recipe recipe = dataAccess.getRecipes().get(0);
        assertNotNull(dataAccess.getRecipe(recipe.getId()));
    }

    @Test
    public void persistentInsertTest() {
        // An inserted recipe exists after connection is closed

        // check for ingredients
        boolean exists = dataAccess.checkIngredientExists("Bananas");
        assertFalse(exists);

        exists = dataAccess.checkIngredientExists("Toast");
        assertFalse(exists);

        Ingredient banana = new Ingredient("Bananas", new ConvertibleUnit(Unit.TBSP, 2));
        Ingredient bread = new Ingredient("Toast", new Count(4));

        // insert ingredients
        boolean inserted = dataAccess.addIngredient(banana);
        assertTrue(inserted);
        inserted = dataAccess.addIngredient(bread);
        assertTrue(inserted);

        ArrayList <Ingredient> ingredients = new ArrayList<>();
        ingredients.add(banana);
        ingredients.add(bread);

        // insert recipe
        Recipe recipe = new Recipe(-1, "Banana Bread", ingredients, "test", false);
        inserted = dataAccess.addRecipe(recipe);
        assertTrue(inserted);

        List<Recipe> recipes = dataAccess.getRecipesWithPartialName("Banana Bread");
        System.out.println(recipes.get(0).getId());
        assertEquals(1, recipes.size());

        // insert recipe ingredients
        inserted = dataAccess.addRecipeIngredient(banana, recipes.get(0));
        assertTrue(inserted);
        inserted = dataAccess.addRecipeIngredient(bread, recipes.get(0));
        assertTrue(inserted);

        dataAccess.close();
        dataAccess.open(testDbName);

        // check for ingredient
        exists = dataAccess.checkIngredientExists("Bananas");
        assertTrue(exists);

        // check for recipe
        recipes = dataAccess.getRecipesWithPartialName("Banana Bread");
        assertEquals(1, recipes.size());;
        assertEquals(2, recipes.get(0).getIngredients().size());
        assertTrue(recipe.equals(recipes.get(0)));
    }

    @Test
    public void persistentModifyTest() {
        Recipe recipe = dataAccess.getRecipes().get(0);
        assertNotNull(recipe);

        // Changes to database are persistent after connection is closed
        assertEquals(0.75, dataAccess.getRecipeIngredients(recipe.getId()).get(0).getAmount(), DELTA);
        dataAccess.updateIngredientQuantity(recipe.getId(), 2.0, "Balsamic Vinegar");
        assertEquals(2.0, dataAccess.getRecipeIngredients(recipe.getId()).get(0).getAmount(), DELTA);

        dataAccess.close();
        dataAccess.open(testDbName);
        assertEquals(dataAccess.getRecipeIngredients(recipe.getId()).get(0).getAmount(), 2, DELTA);
    }

    @Test
    public void persistentDeleteTest() {
        Recipe recipe = dataAccess.getRecipes().get(0);
        assertNotNull(recipe);

        // Deletions from database are persistent after connection is closed
        assertNotNull(dataAccess.getRecipe(recipe.getId()));
        dataAccess.deleteRecipe(recipe.getId());
        assertNull(dataAccess.getRecipe(recipe.getId()));

        dataAccess.close();
        dataAccess.open(testDbName);
        assertNull(dataAccess.getRecipe(recipe.getId()));

    }

}
