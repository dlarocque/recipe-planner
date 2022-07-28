package com.example.recipe_planner.acceptance;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import android.text.format.DateFormat;

import androidx.test.espresso.contrib.RecyclerViewActions;
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
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingListTest {
    private static final String testDbName = "database/RecipesTest";
    private static DataAccess dataAccess;
    private AccessRecipes accessRecipes;
    private AccessIngredients accessIngredients;
    private AccessSchedule accessSchedule;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        dataAccess = Services.getDataAccess();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
        accessSchedule = new AccessSchedule();
        dataAccess.reset();
    }

    @After
    public void tearDown() {
    }

    public void scheduleMeal() {
        Recipe recipe = accessRecipes.getRecipes().get(0);
        Date date = Calendar.getInstance().getTime();
        accessSchedule.setMeal(date, DaySchedule.Meal.BREAKFAST, recipe);
    }

    public void navigateToShoppingListView(){
        onView(withId(R.id.shoppingList)).perform(click());
    }

    @Test
    public void viewingShoppingListTest() {
        ArrayList<Recipe> recipes = dataAccess.getScheduledRecipes();
        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        for(Recipe recipe : recipes){
            ingredientList.addAll(recipe.getIngredients());
        }

        scheduleMeal();
        navigateToShoppingListView();

        onView(withId(R.id.ingredientShoppingList)).check(matches(isDisplayed()));

        for(Ingredient ingredientInList : ingredientList){
            onView(withText(ingredientInList.getName())).check(matches(isDisplayed()));
        }
    }
}
