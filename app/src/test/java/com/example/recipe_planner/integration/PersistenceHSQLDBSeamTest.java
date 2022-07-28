package com.example.recipe_planner.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.Unit;
import com.example.recipe_planner.persistence.DataAccess;

import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void accessOpenDatabaseTest() {
        // The database in dataAccess is already open
        // Check that the dataAccess exists and we can make calls to HSQLDB
        assertNotNull(dataAccess);

        // Queries aren't null
        assertNotNull(dataAccess.getRecipes());

        // Modifications are true
        assertTrue(dataAccess.deleteRecipe(0));
        assertTrue(dataAccess.deleteIngredient(0, "Water"));
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
        assertFalse(dataAccess.deleteIngredient(0, "Water"));

        // Deletions that occurs when database was closed did not complete
        dataAccess.open(testDbName);
        Recipe recipe = dataAccess.getRecipes().get(0);
        assertNotNull(dataAccess.getRecipe(recipe.getId()));
    }

    @Test
    public void persistentInsertTest() {
        // An inserted recipe exists after connection is closed
        // TODO: lol I don't have insert methods to use here

        dataAccess.close();
        dataAccess.open(testDbName);
    }

    @Test
    public void persistentModifyTest() {
        Recipe recipe = dataAccess.getRecipes().get(0);
        assertNotNull(recipe);

        // Changes to database are persistent after connection is closed
        assertEquals(
                0.75, dataAccess.getRecipeIngredients(recipe.getId()).get(0).getAmount(), DELTA);
        dataAccess.updateIngredientQuantity(recipe.getId(), 2.0, "Balsamic Vinegar");
        assertEquals(
                2.0, dataAccess.getRecipeIngredients(recipe.getId()).get(0).getAmount(), DELTA);

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
