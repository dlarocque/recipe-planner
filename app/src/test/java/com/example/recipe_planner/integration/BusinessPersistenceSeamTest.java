package com.example.recipe_planner.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;
import com.example.recipe_planner.persistence.DataAccessStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BusinessPersistenceSeamTest {
    private static final double DELTA = 0.001;  // to account for rounding errors
    private DataAccess dataAccess;
    private AccessIngredients accessIngredients;
    private AccessRecipes accessRecipes;
    private AccessSchedule accessSchedule;
    private Date today;

    @Before
    public void setUp() {
        Services.createDataAccess(new DataAccessStub()); // test against stub
        dataAccess = Services.getDataAccess();
        dataAccess.reset();
        today = Calendar.getInstance().getTime();
    }

    @After
    public void tearDown() {
        dataAccess.close();
    }

    @Test
    public void testGetModifyAndDeleteRecipe() {
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();

        // get a recipe
        Recipe recipeBusiness = accessRecipes.getRecipe(0);
        Recipe recipePersistence = dataAccess.getRecipe(0);
        assertEquals(recipeBusiness, recipePersistence);
        assertEquals(0, recipeBusiness.getId(), 0);

        // modify the recipe (change quantity of an ingredient)
        List<Ingredient> ingredients = accessRecipes.getRecipeIngredients(0);
        Ingredient before = ingredients.get(0);
        accessIngredients.updateIngredientQuantity(recipeBusiness.getId(), 40.0, "Balsamic Vinegar");
        ingredients = accessRecipes.getRecipeIngredients(0);
        Ingredient after = ingredients.get(0);
        assertEquals(40.0, after.getAmount(), 0);
        assertEquals(before.getName(), after.getName());

        // delete the recipe
        accessRecipes.deleteRecipe(recipeBusiness.getId());
        assertNull(accessRecipes.getRecipe(recipeBusiness.getId()));
    }

    @Test
    public void testModifyDeleteIngredients() {
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
        // get a recipe
        Recipe recipe = accessRecipes.getRecipe(1);
        assertEquals("Sweet Honey French Bread", recipe.getName());
        assertEquals(1, recipe.getId());

        // select ingredient
        Ingredient modify = recipe.getIngredients().get(3);
        assertEquals(0.66666666, modify.getAmount(), DELTA);

        // modify ingredient quantity
        accessIngredients.updateIngredientQuantity(1, 50.0, "Salt");
        Ingredient check = accessRecipes.getRecipeIngredients(1).get(3);
        assertEquals(50.0, check.getAmount(), DELTA);

        // delete the ingredient
        accessIngredients.deleteIngredient(1, check.getName(), check.getAmount(), check.getUnit().getClass().getSimpleName());
        List<Ingredient> updated = accessRecipes.getRecipe(1).getIngredients();
        for ( Ingredient current : updated ) {
            assertNotEquals("Salt", current.getName());
        }
    }

    @Test
    public void testOverwriteScheduledMeal() {
        accessRecipes = new AccessRecipes();
        accessSchedule = new AccessSchedule();

        // schedule a recipe for a meal
        Recipe toSchedule = accessRecipes.getRecipe(1);
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, toSchedule);
        DaySchedule todaysMeals = accessSchedule.getDayScheduleOrDefault(today);
        assertEquals(toSchedule.getId(), todaysMeals.getMeal(DaySchedule.Meal.LUNCH).getId());

        // schedule another one for the same meal (overwrite)
        Recipe overwrite = accessRecipes.getRecipe(0);
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, overwrite);
        todaysMeals = accessSchedule.getDayScheduleOrDefault(today);

        // check that the second recipe is found in that meal slot
        assertEquals(overwrite.getId(), todaysMeals.getMeal(DaySchedule.Meal.LUNCH).getId());

        // de-schedule the meal
        accessSchedule.descheduleMeal(today, DaySchedule.Meal.LUNCH);
        todaysMeals = accessSchedule.getDayScheduleOrDefault(today);
        assertNull(todaysMeals.getMeal(DaySchedule.Meal.LUNCH));
    }

    @Test
    public void testDescheduleNonExistingMeal() {
        accessRecipes = new AccessRecipes();
        accessSchedule = new AccessSchedule();
        // get a recipe
        Recipe recipe = accessRecipes.getRecipe(3);
        assertEquals(3, recipe.getId());

        // schedule it
        accessSchedule.setMeal(today, DaySchedule.Meal.DINNER, recipe);
        DaySchedule day = accessSchedule.getDayScheduleOrDefault(today);
        assert(day.mealIsScheduled(DaySchedule.Meal.DINNER));
        assertEquals(recipe, day.getMeal(DaySchedule.Meal.DINNER));

        // de-schedule the meal
        accessSchedule.descheduleMeal(today, DaySchedule.Meal.DINNER);
        day = accessSchedule.getDayScheduleOrDefault(today);
        assertNull(day.getMeal(DaySchedule.Meal.DINNER));

        // de-schedule the meal again
        accessSchedule.descheduleMeal(today, DaySchedule.Meal.DINNER);
        day = accessSchedule.getDayScheduleOrDefault(today);
        assertNull(day.getMeal(DaySchedule.Meal.DINNER));
    }

    @Test
    public void testScheduleThenDeleteRecipe() {
        accessRecipes = new AccessRecipes();
        accessSchedule = new AccessSchedule();

        // schedule a recipe for a meal
        Recipe recipe = accessRecipes.getRecipe(2);
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, recipe);
        DaySchedule todaysMeals = accessSchedule.getDayScheduleOrDefault(today);
        assertEquals(recipe.getId(), todaysMeals.getMeal(DaySchedule.Meal.LUNCH).getId());

        // delete the recipe
        accessRecipes.deleteRecipe(recipe.getId());
        assertNull(accessRecipes.getRecipe(recipe.getId()));

        // check that the meal schedule still exists in the schedule
        todaysMeals = accessSchedule.getDayScheduleOrDefault(today);
        assertEquals(recipe, todaysMeals.getMeal(DaySchedule.Meal.LUNCH));

        // this is slightly different than the real db, where foreign-key constraints
        // mean that any deleted recipes are also removed from the schedules.
    }
}
