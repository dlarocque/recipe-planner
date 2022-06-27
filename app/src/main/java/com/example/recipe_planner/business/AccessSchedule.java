package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.persistence.DataAccessStub;

import java.util.Date;

public class AccessSchedule {
    private final DataAccessStub dataAccess;

    public AccessSchedule() {
        dataAccess = Services.getDataAccess();
    }

    public DaySchedule getDayScheduleOrDefault(Date date) {
        return dataAccess.getDayScheduleOrDefault(date);
    }
}
