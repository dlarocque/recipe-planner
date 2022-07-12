package com.example.recipe_planner.persistence;

import android.util.Log;

import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.IUnit;

import com.example.recipe_planner.objects.measurements.Unit;
import com.example.recipe_planner.utils.CalendarUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAccessDB implements DataAccess {

    /**
     * this field contains sql script to populate the DB with initial data this exact script inside
     * the Recipes.script file itself was producing errors with keys, so we will do it separately
     * here.
     */
    private static final String[] populateScript = {
        "INSERT INTO RECIPES (ID, NAME, INSTRUCTIONS, IS_DEFAULT) VALUES(NULL, 'Grilled Basil Chicken',\n"
                + "    'After washing basil and tomatoes, blot them dry with clean paper towel.\n'\n"
                + "    + '\\n'\n"
                + "    + 'Using a clean cutting board, cut tomatoes into quarters.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'For marinade, place first six ingredients in a blender. Cover and process until well blended.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'Place chicken breasts in a shallow dish; orange do not rinse raw poultry. Cover with marinade. Cover dish. Refrigerate about 1 hour, turning occasionally. Wash dish after touching raw poultry.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'orange quote icon Wash hands with soap and water after handling uncooked chicken.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'Place chicken on an oiled grill rack over medium heat. Do not reuse marinades used on raw foods. Grill chicken 4-6 minutes per side. Cook until internal temperature reaches 165 °F as measured with a food thermometer. ',\n"
                + "    1\n"
                + ")",
        "INSERT INTO RECIPES VALUES(NULL, 'Sweet Honey French Bread',\n"
                + "    'Add to your bread machine per manufacturer instructions.\\n'\n"
                + "    + 'While bread is baking drizzle with honey if desired.',\n"
                + "    1\n"
                + ")",
        "INSERT INTO RECIPES VALUES (NULL, 'Crushed Heirloom Potatoes',\n"
                + "    'Boil potatoes until they are just tender 2040 minutes depending on variety drain and then return them to pot.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'Using a large wooden spoon coarsely crush potatoes in pot.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'Stir in cheese nuts oil and arugula and toss to blend.\\n'\n"
                + "    + '\\n'\n"
                + "    + 'Salt and pepper to taste.',\n"
                + "    1\n"
                + ")",
        "INSERT INTO RECIPES (NAME, INSTRUCTIONS, IS_DEFAULT) VALUES('Heirloom Apple Pie',\n"
                + "    '1. Mix apples white and brown sugar flour and cinnamon all together in a large bowl and pour into pie crust in pan.\\n'\n"
                + "    + '\\n'\n"
                + "    + '2. Dot with butter and cover with top crust sealing and fluting edges.\\n'\n"
                + "    + '\\n'\n"
                + "    + '3. Slit a few holes in top crust.\\n'\n"
                + "    + '\\n'\n"
                + "    + '4. Brush cream lightly on top crust all over and sprinkle with sugar.\\n'\n"
                + "    + '\\n'\n"
                + "    + '5. Bake at 450 for 15 minutes.\\n'\n"
                + "    + '\\n'\n"
                + "    + '6. Reduce heat to 350 and bake another 5055 minutes until bubbly and apples are soft.\\n'\n"
                + "    + '\\n'\n"
                + "    + '7. Savor every bite.',\n"
                + "    1\n"
                + ")",

        "INSERT INTO INGREDIENTS VALUES (NULL, 'Balsamic Vinegar')",
        "INSERT INTO INGREDIENTS VALUES (NULL, 'Balsamic Vinegar')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Basil Leaves')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Olive Oil')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Plum Tomatoes')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Boneless Skinless Chicken Breast')",
        "INSERT INTO RECIPEINGREDIENTS VALUES (0, 0, 0.75, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES (0, 1, 0.25,'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES (0, 2, 2, 'TBSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES (0, 3, 4, 'COUNT')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES (0, 4, 4, 'COUNT')",
        "INSERT INTO INGREDIENTS VALUES (NULL, 'Water')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Honey')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Salt')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'White Sugar')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Bread Flour')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Active Yeast')",
        "INSERT INTO RECIPEINGREDIENTS VALUES(1, 5, 0.75, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 6, 2, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 2, 2, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 7, 2/3, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 8, 2/3, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 9, 2, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 10, 1.5, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(1, 11, 1, 'CUP')",
        "INSERT INTO INGREDIENTS VALUES (NULL, 'Unpeeled Potato')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Gorgonzola')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Pecan')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Extra Virgin Olive Oil')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Baby Arugula')",
        "INSERT INTO RECIPEINGREDIENTS VALUES(2, 5, 0.75, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 12, 907, 'GRAM')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 13, 2, 'OUNCE')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 14, 0.5, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 15, 0.25, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 16, 2, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(2, 7, 0.5, 'TSP')",
        "INSERT INTO INGREDIENTS VALUES (NULL, 'Pastry Double Crust Pie')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Apple')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Brown Sugar')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Flour')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Cinnamon')\n"
                + "INSERT INTO INGREDIENTS VALUES (NULL, 'Butter')",
        "INSERT INTO RECIPEINGREDIENTS VALUES(3, 17, 1, 'COUNT')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 18, 6, 'COUNT')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 8, 1/3, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 19, 1/3, 'CUP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 20, 2, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 21, 1, 'TSP')\n"
                + "INSERT INTO RECIPEINGREDIENTS VALUES(3, 22, 1, 'TBSP')"
    };

    private final String TAG = this.getClass().getSimpleName();
    private Connection connection;

    public DataAccessDB() {}

    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            connection = DriverManager.getConnection(url, "SA", "");

            // TODO: Don't do this
            ResultSet resultSet =
                    connection.createStatement().executeQuery("SELECT * from recipes");
            if (!resultSet.next()) {
                initData();
            }
        } catch (SQLException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException exception) {
            Log.e(TAG, "Failed to open HSQLDB");
            exception.printStackTrace();
        }
        Log.i(TAG, "Successfully opened HSQLDB database at " + dbPath);
    }

    public void close() {
        try {
            // Commit all changes to the database
            Statement statement = connection.createStatement();
            statement.executeQuery("SHUTDOWN COMPACT");
            connection.close();
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to close HSQLDB");
            sqlException.printStackTrace();
        }
        Log.i(TAG, "Closed HSQLDB database");
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        Recipe recipe = null;
        Statement statement;
        ResultSet recipeResult;
        String recipeName, instructions;
        ArrayList<Ingredient> ingredients;
        boolean isDefault;

        try {
            statement = connection.createStatement();

            recipeResult =
                    statement.executeQuery("SELECT * FROM RECIPES WHERE ID=" + recipeId + ";");

            // Get all the components of a recipe, create a recipe, and add it to our list of
            // recipes
            if (recipeResult.next()) {
                recipeId = recipeResult.getInt("ID");
                recipeName = recipeResult.getString("NAME");
                instructions = recipeResult.getString("INSTRUCTIONS");
                isDefault = recipeResult.getBoolean("IS_DEFAULT");
                ingredients = new ArrayList<>(getRecipeIngredients(recipeId));

                recipe = new Recipe(recipeId, recipeName, ingredients, instructions, isDefault);
            } else {
                Log.w(TAG, "Recipe with id " + recipeId + " does not work");
            }
            recipeResult.close();
            statement.close();
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to get recipe with id " + recipeId);
            sqlException.printStackTrace();
        }

        return recipe;
    }

    @Override
    public List<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<Ingredient> ingredients;
        int recipeId;
        String recipeName, instructions;
        boolean isDefault;
        Statement statement;
        ResultSet allRecipes;

        // Build list of recipes
        try {
            // Get all recipes
            statement = connection.createStatement();
            allRecipes = statement.executeQuery("SELECT * FROM RECIPES");

            while (allRecipes.next()) {
                // Get all the components of a recipe, create a recipe, and add it to our list of
                // recipes
                recipeId = allRecipes.getInt("ID");
                recipeName = allRecipes.getString("NAME");
                instructions = allRecipes.getString("INSTRUCTIONS");
                isDefault = allRecipes.getBoolean("IS_DEFAULT");
                ingredients = new ArrayList<>(getRecipeIngredients(recipeId));

                Recipe recipe =
                        new Recipe(recipeId, recipeName, ingredients, instructions, isDefault);
                recipes.add(recipe);
            }

            allRecipes.close();
            statement.close();
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to get all recipes from HSQLDB");
            sqlException.printStackTrace();
        }

        return recipes;
    }

    @Override
    public List<Recipe> getRecipesWithPartialName(String recipePartialName) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<Ingredient> ingredients;
        int recipeId;
        String recipeName, instructions;
        boolean isDefault;
        Statement statement;
        ResultSet allRecipes;

        if (recipePartialName == null) {
            return recipes;
        }

        // Build list of recipes
        try {
            // Get all recipes
            statement = connection.createStatement();
            allRecipes =
                    statement.executeQuery(
                            "SELECT * FROM RECIPES WHERE LCASE(NAME) LIKE '%"
                                    + recipePartialName.toLowerCase()
                                    + "%'");

            while (allRecipes.next()) {
                // Get all the components of a recipe, create a recipe, and add it to our list of recipes
                recipeId = allRecipes.getInt("ID");
                recipeName = allRecipes.getString("NAME");
                instructions = allRecipes.getString("INSTRUCTIONS");
                isDefault = allRecipes.getBoolean("IS_DEFAULT");
                ingredients = new ArrayList<>(getRecipeIngredients(recipeId));

                Recipe recipe =
                        new Recipe(recipeId, recipeName, ingredients, instructions, isDefault);
                recipes.add(recipe);
            }

            allRecipes.close();
            statement.close();
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to get recipes searched by partial name from HSQLDB");
            sqlException.printStackTrace();
        }

        return recipes;
    }

    @Override
    public List<Ingredient> getRecipeIngredients(int recipeId) {
        ArrayList<Ingredient> ingredients = null;
        Ingredient ingredient;
        int ingredientId;
        double quantity;
        String unit, name;
        Statement statement;
        ResultSet recipeIngredients, ingredientName;

        // Build the list of ingredients
        try {
            statement = connection.createStatement();
            recipeIngredients =
                    statement.executeQuery(
                            "SELECT INGREDIENTID, QUANTITY, UNIT FROM RECIPEINGREDIENTS WHERE RECIPEID="
                                    + recipeId
                                    + ";");

            ingredients = new ArrayList<>();
            while (recipeIngredients.next()) {
                ingredientId = recipeIngredients.getInt("INGREDIENTID");
                quantity = recipeIngredients.getDouble("QUANTITY");
                unit = recipeIngredients.getString("UNIT");
                name = null;

                ingredientName =
                        statement.executeQuery(
                                "SELECT NAME FROM INGREDIENTS WHERE ID=" + ingredientId + ";");
                if (ingredientName.next()) {
                    name = ingredientName.getString("NAME");
                } else {
                    Log.w(
                            TAG,
                            "Ingredient name for id "
                                    + ingredientId
                                    + " was not found in database");
                }

                ingredient = new Ingredient(name, unitFactory(unit, quantity)); // name can be NULL
                ingredients.add(ingredient);
            }

            recipeIngredients.close();
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to retrieve ingredients for recipe with id " + recipeId);
            sqlException.printStackTrace();
        }

        return ingredients;
    }

    @Override
    public boolean deleteRecipe(int recipeId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM RECIPES WHERE ID=" + recipeId + ";");
            statement.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteIngredient(int recipeID, String name, double quantity, String unit) {
        Statement statement;
        ResultSet ingredientIDSet;
        int ingredientID = 0;
        try {
            statement = connection.createStatement();
            ingredientIDSet =
                    statement.executeQuery("SELECT ID FROM INGREDIENTS WHERE NAME='" + name + "';");
            if (ingredientIDSet.next()) {
                ingredientID = ingredientIDSet.getInt("ID");
            }
            statement.executeUpdate(
                    "DELETE FROM RECIPEINGREDIENTS WHERE RECIPEID="
                            + recipeID
                            + " AND INGREDIENTID="
                            + ingredientID
                            + " AND QUANTITY="
                            + quantity
                            + " AND UNIT='"
                            + unit
                            + "';");
            statement.close();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateIngredientQuantity(int recipeID, double quantity, String ingredientName) {
        Statement statement;
        ResultSet ingredientIDSet = null;
        int ingredientID = -1;
        try {
            statement = connection.createStatement();
            ingredientIDSet =
                    statement.executeQuery(
                            "SELECT ID FROM INGREDIENTS WHERE NAME='" + ingredientName + "';");
            if (ingredientIDSet.next()) {
                ingredientID = ingredientIDSet.getInt("ID");
            }
            statement.executeUpdate(
                    "UPDATE RECIPEINGREDIENTS SET QUANTITY="
                            + quantity
                            + " WHERE INGREDIENTID="
                            + ingredientID
                            + " AND RECIPEID="
                            + recipeID
                            + ";");
            statement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public DaySchedule getDaySchedule(Date date) {
        DaySchedule daySchedule = null;
        String dateKey = CalendarUtils.formattedDate(date);
        Statement statement;
        ResultSet meals;
        int breakfastRecipeId, lunchRecipeId, dinnerRecipeId;
        Recipe breakfast = null, lunch = null, dinner = null;

        try {
            statement = connection.createStatement();
            meals =
                    statement.executeQuery(
                            "SELECT BREAKFAST_RECIPE_ID, LUNCH_RECIPE_ID, DINNER_RECIPE_ID FROM DAY_SCHEDULES WHERE DAY='"
                                    + dateKey
                                    + "';");

            if (meals.next()) {
                breakfastRecipeId = meals.getInt("BREAKFAST_RECIPE_ID");
                if (!meals.wasNull()) breakfast = getRecipe(breakfastRecipeId);

                lunchRecipeId = meals.getInt("LUNCH_RECIPE_ID");
                if (!meals.wasNull()) lunch = getRecipe(lunchRecipeId);

                dinnerRecipeId = meals.getInt("DINNER_RECIPE_ID");
                if (!meals.wasNull()) dinner = getRecipe(dinnerRecipeId);

                daySchedule = new DaySchedule(breakfast, lunch, dinner);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return daySchedule;
    }

    @Override
    public void initializeDaySchedule(Date date) {
        Statement statement;
        String dateKey = CalendarUtils.formattedDate(date);

        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO DAY_SCHEDULES (DAY, BREAKFAST_RECIPE_ID, LUNCH_RECIPE_ID, DINNER_RECIPE_ID) VALUES('"
                            + dateKey
                            + "', NULL, NULL, NULL)");
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to insert day schedule");
            sqlException.printStackTrace();
        }
    }

    @Override
    public void setDayScheduleMeal(Date date, DaySchedule.Meal meal, Recipe recipe) {
        Statement statement;
        ResultSet recipeExists;
        String dateKey = CalendarUtils.formattedDate(date);
        String mealToUpdate;

        try {
            statement = connection.createStatement();

            // If there is no entry for given date, create one
            recipeExists =
                    statement.executeQuery(
                            "SELECT * FROM DAY_SCHEDULES WHERE DAY='" + dateKey + "';");
            if (!recipeExists.next()) {
                initializeDaySchedule(date);
            }

            statement.executeUpdate(
                    "UPDATE DAY_SCHEDULES "
                            + "SET "
                            + meal.toString()
                            + "_RECIPE_ID="
                            + recipe.getId()
                            + " "
                            + "WHERE DAY='"
                            + dateKey
                            + "';");
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to set day schedule meal for day " + dateKey);
            sqlException.printStackTrace();
        }
    }

    @Override
    public void setDayScheduleMealNull(Date date, DaySchedule.Meal meal) {
        Statement statement;
        String dateKey = CalendarUtils.formattedDate(date);

        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE DAY_SCHEDULES "
                            + "SET "
                            + meal.toString()
                            + "_RECIPE_ID=NULL "
                            + "WHERE DAY='"
                            + dateKey
                            + "';");
        } catch (SQLException sqlException) {
            Log.e(TAG, "Failed to set day schedule meal for day " + dateKey);
            sqlException.printStackTrace();
        }
    }


    private IUnit unitFactory(String unit, double quantity) {
        Unit type = null;

        switch(unit) {
            case "CUP":
                type = Unit.CUP;
            case "ML":
                type = Unit.ML;
            case "GRAM":
                type = Unit.GRAM;
            case "OUNCE":
                type = Unit.OUNCE;
            case "TSP":
                type = Unit.TSP;
            case "TBSP":
                type = Unit.TBSP;
        }
        return new ConvertibleUnit(type, quantity);
    }

    /**
     * If no data is present, populate the database with initial data
     */
    private void initData() {
        Statement statement;

        try {
            statement = connection.createStatement();
            for (String script : populateScript) {
                statement.executeUpdate(script);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
