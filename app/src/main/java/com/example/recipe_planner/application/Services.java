package com.example.recipe_planner.application;

import android.util.Log;

import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessStub;

public class Services {
    private static DataAccess dataAccessService = null;

    public static DataAccess createDataAccess(String dbName) {
        if (dataAccessService == null) {
            dataAccessService = new DataAccessStub(dbName);
            dataAccessService.open(dbName);
        }
        return dataAccessService;
    }

    public static DataAccess getDataAccess(String dbName) {
        if (dataAccessService == null) {
            Log.d("DatabaseConnection", "No connection to database has been established");
            System.exit(1);
        }
        return dataAccessService;
    }

    public static void closeDataAccess() {
        if (dataAccessService != null) {
            dataAccessService.close();
        }
        dataAccessService = null;
    }
}
