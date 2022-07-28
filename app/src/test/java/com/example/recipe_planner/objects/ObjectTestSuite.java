package com.example.recipe_planner.objects;

import com.example.recipe_planner.objects.measurements.TestConvertibleUnit;
import com.example.recipe_planner.objects.measurements.TestCount;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    TestConvertibleUnit.class,
    TestCount.class,
    TestIngredient.class,
    TestRecipe.class,
    TestDaySchedule.class,
    TestSchedule.class
})
public class ObjectTestSuite {
    @BeforeClass
    public static void beginTests() {
        System.out.println("Running object tests...");
    }

    @AfterClass
    public static void completeTests() {
        System.out.println("Object tests complete!");
    }
}
