package com.example.recipe_planner.integration;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.persistence.DataAccess;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class BusinessPersistenceSeamTest extends TestCase {
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        System.out.println("\nStarting Business Persistence seam test");
        Services.createDataAccess(); // HSQLDB
        dataAccess = Services.getDataAccess();
        dataAccess.reset();
    }

    @Test
    public void TestAccessRecipes() {
        // Add recipe
        // Query that recipe exists
        // Query what ingredients are in the recipe
        // Modify an ingredient in the recipe
        // Query that the ingredient row was modified
        // Query that there are the same number of ingredients
    }
}
