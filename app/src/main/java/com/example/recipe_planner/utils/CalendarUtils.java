package com.example.recipe_planner.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/** Calendar Utilities, primarily used for dealing with meal schedules */
public class CalendarUtils {
    public static String formattedDate(Date date) {
        return DateFormat.format("EEE MMM dd", date).toString();
    }

    public static Date incrementDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
