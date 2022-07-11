package com.example.recipe_planner.application;

/**
 * This is where the database name and path are defined, and how the app calls the Services component
 * In the future, this facilitates adding a CLI version of the app
 */
public class Main {
    public static final String dbName = "Recipes";
    private static String dbPathName = "database/Recipes";

    public static void startUp() {
        Services.createDataAccess(dbName);
    }

    public static void shutDown() {
        Services.closeDataAccess();
    }
}
