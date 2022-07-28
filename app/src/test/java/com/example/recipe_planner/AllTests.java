package com.example.recipe_planner;

import com.example.recipe_planner.business.BusinessTestSuite;
import com.example.recipe_planner.integration.IntegrationTestSuite;
import com.example.recipe_planner.objects.ObjectTestSuite;
import com.example.recipe_planner.persistence.DataAccessTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ObjectTestSuite.class,
    DataAccessTest.class,
    BusinessTestSuite.class,
    IntegrationTestSuite.class
})
public class AllTests {
    @BeforeClass
    public static void beginTests() {
        System.out.println("Running all tests...");
    }

    @AfterClass
    public static void completeTests() {
        System.out.println("All tests complete!");
    }
}
