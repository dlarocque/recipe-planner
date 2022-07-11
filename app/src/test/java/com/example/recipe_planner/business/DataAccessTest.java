package com.example.recipe_planner.business;

import static org.junit.Assert.assertNotNull;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import java.util.List;

public class DataAccessTest {

    private DataAccess dataAccess;

    @Before
    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess");

        dataAccess = new DataAccessStub(Main.dbName);
        dataAccess.open(Main.getDBPathName());
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    @Test
    public void testGetIngredientsFromInvalidRecipeIndex(){
        List<Ingredient> ingredients;

        ingredients = dataAccess.getRecipeIngredients(-1);
        assertEquals(0, ingredients.size());
    }

    @Test
    public void testGetDefaultIngredients() {
        List<Ingredient> ingredients;

        double delta = 0.001;

        // get first default recipe
        ingredients = dataAccess.getRecipeIngredients(0);
        assertNotNull(ingredients);

        // test all ingredients
        Ingredient ingredient = ingredients.get(0);
        assertEquals("Balsamic Vinegar", ingredient.getName());
        assertEquals(0.75, ingredient.getAmount(), delta);

        ingredient = ingredients.get(1);
        assertEquals("Basil Leaves", ingredient.getName());
        assertEquals(0.25, ingredient.getAmount(), delta);

        ingredient = ingredients.get(2);
        assertEquals("Olive Oil", ingredient.getName());
        assertEquals(2.0, ingredient.getAmount(), delta);

        ingredient = ingredients.get(3);
        assertEquals("Plum Tomatoes", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), delta);

        ingredient = ingredients.get(4);
        assertEquals("Boneless Skinless Chicken Breast", ingredient.getName());
        assertEquals(4.0, ingredient.getAmount(), delta);
    }
}
