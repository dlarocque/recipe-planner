package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.Millilitre;
import com.example.recipe_planner.objects.measurements.Tablespoon;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestDaySchedule {
    private Recipe firstValidRecipe;
    private Recipe secondValidRecipe;
    private Recipe thirdValidRecipe;
    private DaySchedule daySchedule;

    protected void init() {
        String firstValidRecipeName = "secret cocktail";
        String firstValidRecipeInstructions = "mix well, bring to a boil and enjoy! Serves 3.";
        ArrayList<Ingredient> firstValidRecipeIngredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("chili peppers", new Cup(0.5)),
                                new Ingredient("cheddar", new Gram(400)),
                                new Ingredient("ketchup", new Cup(4))));
        firstValidRecipe = new Recipe(firstValidRecipeName, firstValidRecipeIngredients, firstValidRecipeInstructions);

        String secondValidRecipeName = "fun chunks";
        String secondValidRecipeInstructions =
                "let marinate outdoors for 2 days, deep-fry and serve lukewarm. Serves 8.";
        ArrayList<Ingredient> secondValidRecipeIngredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("McDonald's Chicken Nuggets", new Gram(400)),
                                new Ingredient("Pickle Juice", new Millilitre(250)),
                                new Ingredient("Gravy", new Cup(3.5))));
        secondValidRecipe = new Recipe(secondValidRecipeName, secondValidRecipeIngredients, secondValidRecipeInstructions);

        String thirdValidRecipeName = "Peanut Butter Sandwich";
        String thirdValidRecipeInstructions =
                "Spread peanut butter on one slice of bread, then place second slice of break over the first.";
        ArrayList<Ingredient> thirdValidRecipeIngredients =
                new ArrayList<>(
                        Arrays.asList(
                                new Ingredient("Rye Bread", new Count(2)),
                                new Ingredient("Peanut Butter", new Tablespoon(2))));
        thirdValidRecipe = new Recipe(thirdValidRecipeName, thirdValidRecipeIngredients, thirdValidRecipeInstructions);

        daySchedule = new DaySchedule();
    }

    @Test
    public void TestTypicalDaySchedule() {
        init();

        Recipe breakfast = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, breakfast);
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.LUNCH));
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.DINNER));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), breakfast.getName());

        Recipe lunch = secondValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.LUNCH, lunch);
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.LUNCH));
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.DINNER));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.LUNCH), lunch.getName());

        Recipe dinner = thirdValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.DINNER, dinner);
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.LUNCH));
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.DINNER));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.DINNER), dinner.getName());
    }

    @Test
    public void TestRescheduledMeal() {
        init();

        Recipe meal = firstValidRecipe;
        Recipe replacement = secondValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.DINNER, meal);
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.DINNER), meal.getName());

        daySchedule.setMeal(DaySchedule.Meal.DINNER, replacement);
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.DINNER), replacement.getName());
    }

    @Test
    public void TestScheduledRecipeEdit() {
        init();

        Recipe meal = firstValidRecipe;
        String updatedName = "New Name";
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);

        meal.setName(updatedName);
        assertEquals(updatedName, daySchedule.getMealName(DaySchedule.Meal.BREAKFAST));
    }

    @Test
    public void TestSameRecipeDifferentDay() {
        init();

        Recipe meal = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        DaySchedule differentDay = new DaySchedule();
        differentDay.setMeal(DaySchedule.Meal.DINNER, meal);

        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), differentDay.getMealName(DaySchedule.Meal.DINNER));
    }

    @Test
    public void TestSameRecipeDifferentDayEdited() {
        init();

        Recipe meal = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        DaySchedule differentDay = new DaySchedule();
        differentDay.setMeal(DaySchedule.Meal.DINNER, meal);

        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), differentDay.getMealName(DaySchedule.Meal.DINNER));
        meal.setName("New Name");
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), differentDay.getMealName(DaySchedule.Meal.DINNER));
    }

    @Test
    public void TestSameRecipeOneDay() {
        init();

        Recipe meal = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        daySchedule.setMeal(DaySchedule.Meal.LUNCH, meal);

        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), daySchedule.getMealName(DaySchedule.Meal.LUNCH));
    }

    @Test
    public void TestSameRecipeOneDayEdited() {
        init();

        Recipe meal = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        daySchedule.setMeal(DaySchedule.Meal.LUNCH, meal);

        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), daySchedule.getMealName(DaySchedule.Meal.LUNCH));
        meal.setName("New Name");
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), daySchedule.getMealName(DaySchedule.Meal.LUNCH));
    }

    @Test
    public void TestNullRecipeScheduled() {
        init();

        Recipe meal = null;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), "");
    }

    @Test
    public void TestRescheduleMealNull() {
        init();

        Recipe meal = firstValidRecipe;
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, meal);
        assertTrue(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), meal.getName());

        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, null);
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertEquals(daySchedule.getMealName(DaySchedule.Meal.BREAKFAST), "");
    }
}
