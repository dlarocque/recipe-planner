package com.example.recipe_planner;

import com.example.recipe_planner.objects.ObjectTestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ObjectTestSuite.class })
public class AllTests {
    @Before
    private static void beginTests() {
        System.out.println("Running all tests...");
    }
    @After
    private static void completeTests() {
        System.out.println("All tests complete!");
    }
}
