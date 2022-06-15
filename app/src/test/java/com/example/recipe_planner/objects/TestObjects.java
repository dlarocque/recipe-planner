package com.example.recipe_planner.objects;

import com.example.recipe_planner.objects.*;
import com.example.recipe_planner.objects.measurements.*;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCount.class,
        TestCup.class,
        TestGram.class,
        TestMillilitre.class,
        TestOunce.class,
        TestTablespoon.class,
        TestTeaspoon.class,
        TestIngredient.class,
        TestRecipe.class
        })
public class TestObjects {
    @Before
    private static void beginTests() {
        System.out.println("Running object tests...");
    }
    @After
    private static void completeTests() {
        System.out.println("Object tests complete!");
    }
}
