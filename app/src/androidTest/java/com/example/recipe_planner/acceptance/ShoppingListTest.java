package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipe_planner.R;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.presentation.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingListTest {
    private static final String testDbName = "database/RecipesTest";
    private static DataAccess dataAccess;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    Date date;
    private AccessRecipes accessRecipes;
    private AccessIngredients accessIngredients;
    private AccessSchedule accessSchedule;

    @Before
    public void setUp() {
        dataAccess = Services.getDataAccess();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
        accessSchedule = new AccessSchedule();
        dataAccess.reset();
        date = Calendar.getInstance().getTime();
    }

    @After
    public void tearDown() {}

    public void scheduleMeal(Recipe recipe, DaySchedule.Meal meal) {
        accessSchedule.setMeal(date, meal, recipe);
    }

    public void navigateToShoppingListView() {
        onView(withId(R.id.shoppingList)).perform(click());
    }

    @Test
    public void viewScheduledMealTest() {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) accessRecipes.getRecipes();
        Recipe recipe = recipes.get(0);

        scheduleMeal(recipe, DaySchedule.Meal.BREAKFAST);
        navigateToShoppingListView();

        onView(withId(R.id.ingredientShoppingList)).check(matches(isDisplayed()));

        for (Ingredient ingredient : recipe.getIngredients()) {
            onView(withText(ingredient.getName())).check(matches(isDisplayed()));
        }
    }

    @Test
    public void viewNonOverlappingScheduledMealTest() {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) accessRecipes.getRecipes();
        // The first two meals in the list have non-overlapping ingredients
        Recipe firstRecipe = recipes.get(0);
        Recipe secondRecipe = recipes.get(1);

        scheduleMeal(firstRecipe, DaySchedule.Meal.BREAKFAST);
        scheduleMeal(secondRecipe, DaySchedule.Meal.LUNCH);
        navigateToShoppingListView();

        ArrayList<Ingredient> ingredients = firstRecipe.getIngredients();
        ingredients.addAll(secondRecipe.getIngredients());

        for (Ingredient ingredient : ingredients) {
            onView(withText(ingredient.getName())).check(matches(isDisplayed()));
        }
    }
}
