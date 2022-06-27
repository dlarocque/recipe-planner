package com.example.recipe_planner.objects;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.HashMap;

public class Schedule {
    private final HashMap<String, DaySchedule> schedule;

    public Schedule() {
        this.schedule = new HashMap<>();
    }

    public Schedule(HashMap<String, DaySchedule> schedule) {
        this.schedule = schedule;
    }

    public static String dateHash(Date date) {
        return DateFormat.format("yyyy-dd", date).toString();
    }

    public void setDaySchedule(Date date, DaySchedule daySchedule) {
        this.schedule.put(dateHash(date), daySchedule);
    }

    public DaySchedule getDayScheduleOrDefault(Date date) {
        DaySchedule emptySchedule;
        if (!this.schedule.containsKey(dateHash(date))) {
            emptySchedule = new DaySchedule();
            this.setDaySchedule(date, emptySchedule);
        }

        return this.schedule.get(dateHash(date));
    }
}
