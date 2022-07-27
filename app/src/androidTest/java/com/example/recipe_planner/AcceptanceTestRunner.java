package com.example.recipe_planner;

import com.example.recipe_planner.acceptance.EditRecipeTest;
import com.example.recipe_planner.acceptance.RecipeListTest;
import com.example.recipe_planner.acceptance.ShoppingListTest;
import com.example.recipe_planner.application.Main;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({RecipeListTest.class, EditRecipeTest.class, ShoppingListTest.class})
public class AcceptanceTestRunner {
    private static String testDbPath = "database/RecipesTest";
    private static String testDbName = "RecipesTest";

    @BeforeClass
    public static void beginTests() {
        System.out.println("Running acceptance tests");
        Main.setDbName(testDbName);
        Main.setDBPathName(testDbPath);
    }

    @AfterClass
    public static void completeTests() {
        System.out.println("Completed acceptance tests");
    }
}

