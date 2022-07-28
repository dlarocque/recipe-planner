package com.example.recipe_planner.integration;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.persistence.DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BusinessPersistenceSeamTest {
    private static final String testDbName = "database/RecipesTest";
    private DataAccess dataAccess;

    @Before
    public void setUp() {
        Services.createDataAccess(testDbName); // HSQLDB
        dataAccess = Services.getDataAccess();
        dataAccess.reset();
    }

    @After
    public void tearDown() {
        dataAccess.close();
    }

    @Test
    public void placeholder() {
        assert(true);
    }
}
