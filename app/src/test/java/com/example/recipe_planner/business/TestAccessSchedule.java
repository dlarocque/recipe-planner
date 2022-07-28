package com.example.recipe_planner.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;
import com.example.recipe_planner.utils.CalendarUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TestAccessSchedule {
    private DataAccess dataAccess;
    private AccessSchedule accessSchedule;
    private Date today;

    @Before
    public void setUp() {
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
        Services.createDataAccess(dataAccess);
        accessSchedule = new AccessSchedule();
        today = Calendar.getInstance().getTime();
    }

    @After
    public void tearDown() {
        dataAccess.close();
    }

    @Test
    public void testValidSchedules() {
        // New schedules do not have any meals scheduled
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        assertNotNull(daySchedule);
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST));
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.LUNCH));
        assertFalse(daySchedule.mealIsScheduled(DaySchedule.Meal.DINNER));

        // Updated schedules are still the same object when we add a meal
        assertEquals(daySchedule, accessSchedule.getDayScheduleOrDefault(today));
        daySchedule.setMeal(DaySchedule.Meal.BREAKFAST, dataAccess.getRecipe(0));
        assertEquals(daySchedule, accessSchedule.getDayScheduleOrDefault(today));

        // Updated schedules are still the same object when we deschedule a meal
        accessSchedule.descheduleMeal(today, DaySchedule.Meal.BREAKFAST);
        assertEquals(daySchedule, accessSchedule.getDayScheduleOrDefault(today));

        // Schedules are different for different days
        Date tomorrow = CalendarUtils.incrementDay(today, 1);
        DaySchedule tomorrowSchedule = accessSchedule.getDayScheduleOrDefault(tomorrow);
        assertNotEquals(daySchedule, tomorrowSchedule);
    }

    @Test
    public void testOverwriteMeal() {
        Recipe oldMeal = dataAccess.getRecipe(1);
        Recipe newMeal = dataAccess.getRecipe(2);

        // set today's lunch to a meal
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, oldMeal);
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(today);

        assertEquals(oldMeal, daySchedule.getMeal(DaySchedule.Meal.LUNCH));

        // overwrite today's lunch with a new meal
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, newMeal);
        daySchedule = accessSchedule.getDayScheduleOrDefault(today);

        assertEquals(newMeal, daySchedule.getMeal(DaySchedule.Meal.LUNCH));
    }

    @Test
    public void testOverwriteWithNullMeal() {
        Recipe oldMeal = dataAccess.getRecipe(1);

        // set today's lunch to a meal
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, oldMeal);
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(today);

        assertEquals(oldMeal, daySchedule.getMeal(DaySchedule.Meal.LUNCH));

        // overwrite today's lunch with a new meal
        accessSchedule.setMeal(today, DaySchedule.Meal.LUNCH, null);
        daySchedule = accessSchedule.getDayScheduleOrDefault(today);

        assertNull(daySchedule.getMeal(DaySchedule.Meal.LUNCH));
    }

    @Test
    public void testRemoveNonExistingMeal() {
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        assertNull(daySchedule.getMeal(DaySchedule.Meal.BREAKFAST));

        accessSchedule.descheduleMeal(today, DaySchedule.Meal.BREAKFAST);
        daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        assertNull(daySchedule.getMeal(DaySchedule.Meal.BREAKFAST));
    }

    @Test
    public void testUpdateNonExistingSchedule() {
        DaySchedule daySchedule;
        Recipe recipe = dataAccess.getRecipe(0);

        // Schedules are still updated even if they don't exist yet
        accessSchedule.setMeal(today, DaySchedule.Meal.BREAKFAST, recipe);
        daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        assertEquals(recipe, daySchedule.getMeal(DaySchedule.Meal.BREAKFAST));

        // Descheduling a meal on a non existing schedule does not cause errors
    }
}
