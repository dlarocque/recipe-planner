package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipe_planner.R;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.presentation.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditRecipeTest {

    private static final String testDbName = "database/RecipesTest";
    private static DataAccess dataAccess;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    private AccessRecipes accessRecipes;
    private AccessIngredients accessIngredients;

    @Before
    public void setUp() {
        dataAccess = Services.getDataAccess();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
    }

    @After
    public void tearDown() {}

    public void navigateToRecipeView() {
        System.out.println(R.id.recipeListRecyclerView);
        onView(withId(R.id.recipeListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void changeRecipeNameTest() {
        navigateToRecipeView();

        onView(withId(R.id.recipe_name_edit)).check(matches(isDisplayed()));

        onView(withId(R.id.recipe_name_edit)).perform(replaceText("Delicious Recipe"));

        onView(withId(R.id.recipe_name_edit))
                .check(matches(withText(containsString("Delicious Recipe"))));
    }

    @Test
    public void changeRecipeInstructionTest() {
        navigateToRecipeView();

        onView(withId(R.id.recipe_instruction_edit)).check(matches(isDisplayed()));

        onView(withId(R.id.recipe_name_edit)).perform(replaceText("Instructions for recipe"));

        onView(withId(R.id.recipe_name_edit))
                .check(matches(withText(containsString("Instructions for recipe"))));
    }

    @Test
    public void editIngredientsTest() {
        navigateToRecipeView();

        List<Recipe> recipes = accessRecipes.getRecipes();

        List<Ingredient> ingredients = recipes.get(0).getIngredients();

        onView(withId(R.id.editIngredients)).perform(click());

        onView(withId(R.id.editIngredientList)).check(matches(isDisplayed()));

        for (Ingredient ingredient : ingredients) {
            onView(withText(ingredient.getName())).check(matches(isDisplayed()));
        }
    }
}
