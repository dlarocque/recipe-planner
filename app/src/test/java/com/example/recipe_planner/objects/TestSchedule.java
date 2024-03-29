package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.example.recipe_planner.utils.CalendarUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestSchedule {
    private Schedule schedule;
    private Date today;

    @Before
    public void init() {
        schedule = new Schedule();
        today = Calendar.getInstance().getTime();
    }

    @Test
    public void TestTypicalSchedule() {

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        assertEquals(daySchedule, schedule.getDayScheduleOrDefault(today));
    }

    @Test
    public void TestDuplicateSchedulesDifferentDays() {
        int dayIncrement = 1;
        Date tomorrow = CalendarUtils.incrementDay(today, dayIncrement);

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        schedule.setDaySchedule(tomorrow, daySchedule);

        assertEquals(
                schedule.getDayScheduleOrDefault(today),
                schedule.getDayScheduleOrDefault(tomorrow));
    }

    @Test
    public void TestUpdatedSchedule() {

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        DaySchedule updatedSchedule = new DaySchedule();
        assertNotEquals(daySchedule, updatedSchedule);

        schedule.setDaySchedule(today, daySchedule);
        assertEquals(schedule.getDayScheduleOrDefault(today), daySchedule);

        schedule.setDaySchedule(today, updatedSchedule);
        assertEquals(schedule.getDayScheduleOrDefault(today), updatedSchedule);
    }

    @Test
    public void TestGetScheduleFromNewDay() {

        int dayIncrement = 1;
        Date tomorrow = CalendarUtils.incrementDay(today, dayIncrement);

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        DaySchedule newDaySchedule = schedule.getDayScheduleOrDefault(tomorrow);
        assertNotEquals(daySchedule, newDaySchedule);
    }

    @Test
    public void TestDeleteSchedule() {

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        assertNotNull(schedule.getDayScheduleOrDefault(today));
        assertEquals(daySchedule, schedule.getDayScheduleOrDefault(today));

        schedule.setDaySchedule(today, null);
        assertNotNull(schedule.getDayScheduleOrDefault(today));
        assertNotEquals(daySchedule, schedule.getDayScheduleOrDefault(today));
    }

    @Test
    public void TestFindAllScheduledRecipes() {

        Recipe one = new Recipe(0, "dejeuner", null, "");
        Recipe two = new Recipe(1, "diner", null, "");
        Recipe three = new Recipe(2, "souper", null, "");

        DaySchedule daySchedule = new DaySchedule(one, two, three);
        schedule.setDaySchedule(today, daySchedule);
        ArrayList<Recipe> results = new ArrayList<>(schedule.getAllScheduledRecipes());

        assertEquals(3, results.size());
        assert (results.contains(one));
        assert (results.contains(two));
        assert (results.contains(three));
    }
}
