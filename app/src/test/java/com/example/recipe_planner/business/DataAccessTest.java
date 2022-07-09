package com.example.recipe_planner.business;

import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

public class DataAccessTest {

    private DataAccess dataAccess;

    @Before
    public void setUp(){
        System.out.println("\nStarting Persistence test DataAccess");

         dataAccess = new DataAccessDB("Recipes");
         dataAccess.open("database/Recipes");
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    @Test
    public void testGetDefaults(){
        List<Recipe> recipes;

        recipes = dataAccess.getRecipes();

        assertNull(recipes);
    }
}
