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

public class TestAccessRecipes {
    private DataAccess dataAccess;
    private AccessRecipes accessRecipes;
    private Date today;

    @Before
    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess");
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
        Services.createDataAccess(dataAccess);
        accessRecipes = new AccessRecipes();
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
        dataAccess.close();
    }

    public void init() {
        dataAccess.open(Main.dbName);
        accessRecipes = new AccessRecipes();
        today = Calendar.getInstance().getTime();
    }

    @Test
    public void TestGetExistingRecipes() {
        init();

        assertNotNull(accessRecipes.getRecipes());
        assertNotNull(accessRecipes.getRecipe(0));

        // Ingredients queried with recipe ID should be the same as those in the recipe
        Recipe recipe = accessRecipes.getRecipe(0);
        ArrayList<Ingredient> recipeIngredients = recipe.getIngredients();
        ArrayList<Ingredient> queriedRecipeIngredients = (ArrayList<Ingredient>) accessRecipes.getRecipeIngredients(0);
        assertEquals(recipeIngredients, queriedRecipeIngredients);
    }

    @Test
    public void TestGetNonExistingRecipes() {
        init();

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
    public void TestDeleteExistingRecipe() {
        init();

        // Deleting a recipe that exists should remove it from the stored recipes
        assertNotNull(accessRecipes.getRecipe(0));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(0));
    }

    @Test
    public void TestDeleteNonExistingRecipe() {
        init();

        // Deleting a recipe that does not exist should not raise errors
        assertNull(accessRecipes.getRecipe(-1));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(-1));

        assertNull(accessRecipes.getRecipe(9999));
        accessRecipes.deleteRecipe(0);
        assertNull(accessRecipes.getRecipe(9999));
    }


}
