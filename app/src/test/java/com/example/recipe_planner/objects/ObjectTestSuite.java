package com.example.recipe_planner.objects;

import com.example.recipe_planner.objects.measurements.TestCount;
import com.example.recipe_planner.objects.measurements.TestCup;
import com.example.recipe_planner.objects.measurements.TestGram;
import com.example.recipe_planner.objects.measurements.TestMillilitre;
import com.example.recipe_planner.objects.measurements.TestOunce;
import com.example.recipe_planner.objects.measurements.TestTablespoon;
import com.example.recipe_planner.objects.measurements.TestTeaspoon;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        TestCount.class,
        TestCup.class,
        TestGram.class,
        TestMillilitre.class,
        TestOunce.class,
        TestTablespoon.class,
        TestTeaspoon.class,
        TestIngredient.class,
        TestRecipe.class
})
public class ObjectTestSuite {
    @Before
    public static void beginTests() {
        System.out.println("Running object tests...");
    }

    @After
    public static void completeTests() {
        System.out.println("Object tests complete!");
    }
}
