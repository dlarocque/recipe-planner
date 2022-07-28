package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipe_planner.R;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.presentation.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationTest {
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
        dataAccess.reset();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void navigateToMealSchedule() {
        onView(withId(R.id.mealSchedule))
                .perform(click());

        onView(withId(R.id.mealScheduleFragment))
                .check(matches(isDisplayed()));
    }

    @Test
    public void navigateToShoppingList() {
        onView(withId(R.id.shoppingList))
                .perform(click());

        onView(withId(R.id.shoppingListFragment))
                .check(matches(isDisplayed()));
    }


    @Test
    public void navigateBackToRecipeList() {
        onView(withId(R.id.mealSchedule))
                .perform(click());
        onView(withId(R.id.recipeList))
                .perform(click());

        onView(withId(R.id.recipeListRecyclerView))
                .check(matches(isDisplayed()));
    }
}