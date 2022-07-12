package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.ArrayList;

public class AccessSavedDaySchedules {
    private final DataAccess dataAccess;

    public AccessSavedDaySchedules() {
        dataAccess = Services.getDataAccess();
    }

    public void saveDaySchedule(DaySchedule daySchedule) {
        int breakfastRecipeId = -1, lunchRecipeId = -1, dinnerRecipeId = -1;
        if (daySchedule.mealIsScheduled(DaySchedule.Meal.BREAKFAST))
            breakfastRecipeId = daySchedule.getMeal(DaySchedule.Meal.BREAKFAST).getId();
        if (daySchedule.mealIsScheduled(DaySchedule.Meal.LUNCH))
            lunchRecipeId = daySchedule.getMeal(DaySchedule.Meal.LUNCH).getId();
        if (daySchedule.mealIsScheduled(DaySchedule.Meal.DINNER))
            dinnerRecipeId = daySchedule.getMeal(DaySchedule.Meal.DINNER).getId();

        dataAccess.saveDaySchedule(breakfastRecipeId, lunchRecipeId, dinnerRecipeId);
    }

    public ArrayList<DaySchedule> getSavedDaySchedules() {
        return (ArrayList<DaySchedule>) dataAccess.getSavedDaySchedules();
    }


}