package com.example.recipe_planner.application;

import com.example.recipe_planner.persistence.DataAccess;
import com.example.recipe_planner.persistence.DataAccessDB;

public class Services {
    private static final String TAG = "Services";
    private static DataAccess dataAccessService = null;

    public static void createDataAccess() {
        if (dataAccessService == null) {
            dataAccessService = new DataAccessDB();
            dataAccessService.open(Main.getDBPathName());
        }
    }

    public static void createDataAccess(String dbName) {
        dataAccessService = new DataAccessDB();
        dataAccessService.open(dbName);
    }

    public static void createDataAccess(DataAccess dataAccess) {
        dataAccessService = dataAccess;
        dataAccessService.open(Main.getDBPathName());
    }

    public static DataAccess getDataAccess() {
        if (dataAccessService == null) {
            System.out.println("Data Access does not exist, exiting");
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

    public static void resetDataAccess() {
        if (dataAccessService != null) {
            dataAccessService.reset();
        }
    }
}
