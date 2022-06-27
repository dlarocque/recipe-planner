package com.example.recipe_planner;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    public static Date selectedDate;

    public static String formattedDate(Date date) {
        return DateFormat.format("EEE, d MMM yyyy", date).toString();
    }

    public static Date incrementDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

}
