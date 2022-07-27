package com.example.recipe_planner.application;

/**
 * This is where the database name and path are defined, and how the app calls the Services
 * component In the future, this facilitates adding a CLI version of the app
 */
public class Main {
    public static String dbName = "Recipes";
    private static String dbPathName = "database/Recipes";

    public static void startUp() {
        Services.createDataAccess();
    }

    public static void shutDown() {
        Services.closeDataAccess();
    }

    public static String getDBPathName() {
        if (dbPathName == null) return dbName;
        else return dbPathName;
    }

    public static void setDbName(String newDbName) {
        dbName = newDbName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
}
