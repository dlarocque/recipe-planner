package com.example.recipe_planner.application;

import android.util.Log;

import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;

public class Services {
    private static final String TAG = "Services";
    private static DataAccess dataAccessService = null;

    public static void createDataAccess() {
        dataAccessService = new DataAccessDB();
        dataAccessService.open(Main.getDBPathName());
    }

    public static DataAccess getDataAccess() {
        if (dataAccessService == null) {
            Log.e(TAG, "No connection to database has been established");
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
