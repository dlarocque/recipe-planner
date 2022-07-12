package com.example.recipe_planner.application;

import android.util.Log;

import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;

public class Services {
    private static final String TAG = "Services";
    private static DataAccess dataAccessService = null;

    public static DataAccess createDataAccess() {
        if (dataAccessService == null) {
            dataAccessService = new DataAccessDB();
            dataAccessService.open(Main.getDBPathName());
        }
        return dataAccessService;
    }

    public static DataAccess getDataAccess() {
        if (dataAccessService == null) {
            Log.d(TAG, "No connection to database has been established");
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
