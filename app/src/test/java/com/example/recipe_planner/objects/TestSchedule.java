package com.example.recipe_planner.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.recipe_planner.utils.CalendarUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TestSchedule {
    private Schedule schedule;
    private Date today;

    protected void init() {
        schedule = new Schedule();
        today = Calendar.getInstance().getTime();
    }

    @Test
    public void TestTypicalSchedule() {
        init();

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        assertEquals(daySchedule, schedule.getDayScheduleOrDefault(today));
    }

    @Test
    public void TestDuplicateSchedulesDifferentDays() {
        init();

        int dayIncrement = 1;
        Date tomorrow = CalendarUtils.incrementDay(today, dayIncrement);

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        schedule.setDaySchedule(tomorrow, daySchedule);

        assertEquals(schedule.getDayScheduleOrDefault(today), schedule.getDayScheduleOrDefault(tomorrow));
    }

    @Test
    public void TestUpdatedSchedule() {
        init();

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
        init();

        int dayIncrement = 1;
        Date tomorrow = CalendarUtils.incrementDay(today, dayIncrement);

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        DaySchedule newDaySchedule = schedule.getDayScheduleOrDefault(tomorrow);
        assertNotEquals(daySchedule, newDaySchedule);
    }

    @Test
    public void TestDeleteSchedule() {
        init();

        DaySchedule daySchedule = schedule.getDayScheduleOrDefault(today);
        schedule.setDaySchedule(today, daySchedule);
        assertNotNull(schedule.getDayScheduleOrDefault(today));
        assertEquals(daySchedule ,schedule.getDayScheduleOrDefault(today));

        schedule.setDaySchedule(today, null);
        assertNotNull(schedule.getDayScheduleOrDefault(today));
        assertNotEquals(daySchedule, schedule.getDayScheduleOrDefault(today));
    }
}
