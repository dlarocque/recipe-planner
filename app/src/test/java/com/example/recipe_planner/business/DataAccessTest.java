package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;

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
    public void setUp(){
        System.out.println("\nStarting Persistence test DataAccess");

         dataAccess = new DataAccessDB(Main.dbName);
         dataAccess.open(Main.getDBPathName());
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    @Test
    public void testGetDefaultIngredients(){
        List<Ingredient> ingredients;

        ingredients = dataAccess.getRecipeIngredients(0);

        for(Ingredient i : ingredients){
            System.out.println(i.getName());
        }

        for(Ingredient i : ingredients){
            System.out.println(i.getAmount());
        }

        System.out.println(ingredients.size());
        assertEquals("Balsamic Vinegar", ingredients.get(0).getName());
    }
}
