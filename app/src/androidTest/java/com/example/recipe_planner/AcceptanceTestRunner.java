package com.example.recipe_planner;

import com.example.recipe_planner.acceptance.EditRecipeTest;
import com.example.recipe_planner.acceptance.MealScheduleTest;
import com.example.recipe_planner.acceptance.ShoppingListTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MealScheduleTest.class, EditRecipeTest.class, ShoppingListTest.class})
public class AcceptanceTestRunner {
    public void RunAcceptanceTests() {
        System.out.println("Running acceptance tests...");
    }
}

