package com.example.recipe_planner.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/** A Schedule that contains a mapping from days to {@link DaySchedule}. */
public class Schedule {
    private static final String dateHashPattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat(dateHashPattern, Locale.CANADA);
    private final HashMap<String, DaySchedule>
            schedule; // mapping of date formatted strings to schedules

    public Schedule() {
        this.schedule = new HashMap<>();
    }

    public Schedule(HashMap<String, DaySchedule> schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns a date formatted string that will make it such that two dates on the same day will
     * have the same key. This prevents more than two dates on the same day to be mapped to more
     * than one {@link DaySchedule}.
     *
     * @param date: The date to be hashed into a string.
     */
    public static String dateHash(Date date) {
        return dateFormat.format(date);
    }

    public void setDaySchedule(Date date, DaySchedule daySchedule) {
        this.schedule.put(dateHash(date), daySchedule);
    }

    public DaySchedule getDayScheduleOrDefault(Date date) {
        DaySchedule emptySchedule;
        // Create and return new schedule for day if there is no schedule yet
        if (!this.schedule.containsKey(dateHash(date))
                || this.schedule.get(dateHash(date)) == null) {
            emptySchedule = new DaySchedule();
            this.setDaySchedule(date, emptySchedule);
        }

        return this.schedule.get(dateHash(date));
    }

    // iterate through the schedule and return a list of all recipes found within
    public List<Recipe> getAllScheduledRecipes() {
        ArrayList<Recipe> scheduledRecipes = new ArrayList<>();

        for (DaySchedule currentDay : schedule.values()) {
            for (DaySchedule.Meal meal : DaySchedule.Meal.values()) {
                if (currentDay.mealIsScheduled(meal)) {
                    scheduledRecipes.add(currentDay.getMeal(meal));
                }
            }
        }
        return (scheduledRecipes.size() > 0) ? scheduledRecipes : null;
    }
}
