package com.example.recipe_planner.business;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    TestAccessSchedule.class,
    TestAccessRecipes.class,
    TestAccessIngredients.class,
})
public class BusinessTestSuite {
    @BeforeClass
    public static void beginTests() {
        System.out.println("Running business tests...");
    }

    @AfterClass
    public static void completeTests() {
        System.out.println("Business tests complete!");
    }
}
