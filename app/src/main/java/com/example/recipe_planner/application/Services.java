package com.example.recipe_planner.application;

import android.util.Log;

import com.example.recipe_planner.persistence.DataAccessStub;

public class Services {
    private static DataAccessStub dataAccessService = null;

    public static DataAccessStub getDataAccess() {
        if (dataAccessService == null) {
            dataAccessService = new DataAccessStub();
        }
        return dataAccessService;
    }
}
