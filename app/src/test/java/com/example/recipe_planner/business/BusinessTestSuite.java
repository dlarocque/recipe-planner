package com.example.recipe_planner.business;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestAccessSchedule.class, TestAccessRecipes.class})
public class BusinessTestSuite {
    @Before
    public void beginTests() {
        System.out.println("Running object tests...");
    }

    @After
    public void completeTests() {
        System.out.println("Object tests complete!");
    }
}
