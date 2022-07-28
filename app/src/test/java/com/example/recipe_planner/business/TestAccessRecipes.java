package com.example.recipe_planner.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestAccessRecipes {
    private DataAccess dataAccess;
    private AccessRecipes accessRecipes;
    private Date today;

    @Before
    public void setUp() {
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
        Services.createDataAccess(dataAccess);
        accessRecipes = new AccessRecipes();
        today = Calendar.getInstance().getTime();
    }

    @After
    public void tearDown() {
        dataAccess.close();
    }

    @Test
    public void testGetExistingRecipes() {
        assertNotNull(accessRecipes.getRecipes());
        assertNotNull(accessRecipes.getRecipe(0));

        // Ingredients queried with recipe ID should be the same as those in the recipe
        Recipe recipe = accessRecipes.getRecipe(0);
        ArrayList<Ingredient> recipeIngredients = recipe.getIngredients();
        ArrayList<Ingredient> queriedRecipeIngredients =
                (ArrayList<Ingredient>) accessRecipes.getRecipeIngredients(0);
        assertEquals(recipeIngredients, queriedRecipeIngredients);
    }

    @Test
    public void testGetNonExistingRecipes() {
        // Getting an recipe id that does not exist should return null
        assertNull(accessRecipes.getRecipe(-1));
        assertNull(accessRecipes.getRecipe(9999));

        // Getting the ingredients of a recipe with an id that does not exist should return null
        assertNotNull(accessRecipes.getRecipeIngredients(-1));
        assertEquals(accessRecipes.getRecipeIngredients(-1).size(), 0);
        assertNotNull(accessRecipes.getRecipeIngredients(9999));
        assertEquals(accessRecipes.getRecipeIngredients(9999).size(), 0);
    }

    @Test
    public void testFindRecipeByIncompleteName() {
        Recipe recipe = accessRecipes.getRecipe(0);
        List<Recipe> results = accessRecipes.getRecipesWithPartialName("Chicken");

        assertEquals(recipe, results.get(0));
    }

    @Test
    public void testFindRecipesWithCommonNames() {
        // all recipe names in default list have multiple words, meaning we should get them all by searching for " "
        List<Recipe> results = accessRecipes.getRecipesWithPartialName(" ");

        assertEquals(4, results.size(), 0);
    }

    @Test
    public void testDeleteExistingRecipe() {
        // Deleting a recipe that exists should remove it from the stored recipes
        assertNotNull(accessRecipes.getRecipe(0));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(0));
    }

    @Test
    public void testGetDeletedRecipe() {
        // get a recipe that was previously deleted
        Recipe deleted = accessRecipes.getRecipe(0);
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(0));
    }

    @Test
    public void getIngredientsOfDeletedRecipe() {
        Recipe deleted = accessRecipes.getRecipe(1);
        accessRecipes.deleteRecipe(1);
        assertNull(accessRecipes.getRecipe(1));

        assertEquals(0, accessRecipes.getRecipeIngredients(1).size(), 0);
    }

    @Test
    public void testDeleteNonExistingRecipe() {
        // Deleting a recipe that does not exist should not raise errors
        assertNull(accessRecipes.getRecipe(-1));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(-1));

        assertNull(accessRecipes.getRecipe(9999));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(9999));
    }
}
