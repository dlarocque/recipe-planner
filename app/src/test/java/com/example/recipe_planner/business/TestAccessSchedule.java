package com.example.recipe_planner.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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
        System.out.println("\nStarting Persistence test DataAccess");
        dataAccess = new DataAccessStub();
        dataAccess.open(Main.dbName);
        Services.createDataAccess(dataAccess);
        accessSchedule = new AccessSchedule();
    }

    @After
    public void tearDown() {
        System.out.println("Finished Persistence test DataAccess (using stub)");
        dataAccess.close();
    }

    public void init() {
        dataAccess.open(Main.dbName);
        accessSchedule = new AccessSchedule();
        today = Calendar.getInstance().getTime();
    }

    @Test
    public void TestValidSchedules() {
        init();

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
    public void TestUpdateNonExistingSchedule() {
        init();

        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        Recipe recipe = dataAccess.getRecipe(0);

        // Schedules are still updated even if they don't exist yet
        accessSchedule.setMeal(today, DaySchedule.Meal.BREAKFAST, recipe);
        daySchedule = accessSchedule.getDayScheduleOrDefault(today);
        assertEquals(recipe, daySchedule.getMeal(DaySchedule.Meal.BREAKFAST));

        // Descheduling a meal on a non existing schedule does not cause errors
    }
}
