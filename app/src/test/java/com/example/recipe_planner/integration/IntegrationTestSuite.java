package com.example.recipe_planner.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessPersistenceSeamTest.class, PersistenceHSQLDBSeamTest.class})
public class IntegrationTestSuite {
    @Before
    public void beginTests() {
        System.out.println("Running integration tests...");
    }

    @After
    public void completeTests() {
        System.out.println("Integration tests complete!");
    }
}
