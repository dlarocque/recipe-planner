package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipe_planner.R;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.presentation.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest {
    private static final String testDbName = "database/RecipesTest";
    private static DataAccess dataAccess;
    private static AccessRecipes accessRecipes;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        dataAccess = Services.getDataAccess();
        accessRecipes = new AccessRecipes();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void viewingListedRecipes() {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) accessRecipes.getRecipes();

        // All recipes are displayed in the recipe list page
        for (Recipe recipe : recipes) {
            onView(withText(recipe.getName()))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void viewingListedRecipe() {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) accessRecipes.getRecipes();
        Recipe recipe = recipes.get(0);
        onView(withText(recipe.getName()))
                .perform(click());
    }
}
