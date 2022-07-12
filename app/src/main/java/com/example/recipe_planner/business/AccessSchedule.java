package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.Date;

public class AccessSchedule {
    private final DataAccess dataAccess;

    public AccessSchedule() {
        dataAccess = Services.getDataAccess();
    }

    public DaySchedule getDayScheduleOrDefault(Date date) {
        DaySchedule daySchedule = dataAccess.getDaySchedule(date);
        if (daySchedule == null) {
            daySchedule = new DaySchedule();
            dataAccess.initializeDaySchedule(date);
        }

        return daySchedule;
    }

    public void setMeal(Date date, DaySchedule.Meal MEAL, Recipe recipe) {
        dataAccess.setDayScheduleMeal(date, MEAL, recipe);
    }

    public void descheduleMeal(Date date, DaySchedule.Meal meal) {
        dataAccess.setDayScheduleMealNull(date, meal);
    }
}
