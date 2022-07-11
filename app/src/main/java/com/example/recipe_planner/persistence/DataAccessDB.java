package com.example.recipe_planner.persistence;

import android.util.Log;

import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Cup;
import com.example.recipe_planner.objects.measurements.Gram;
import com.example.recipe_planner.objects.measurements.IUnit;
import com.example.recipe_planner.objects.measurements.Millilitre;
import com.example.recipe_planner.objects.measurements.Ounce;
import com.example.recipe_planner.objects.measurements.Tablespoon;
import com.example.recipe_planner.objects.measurements.Teaspoon;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class DataAccessDB implements DataAccess{

    private Statement st1, st2, st3;
    private Connection c1;
    private ResultSet rs2, rs3, rs4, rs5;

    private String dbName;
    private String dbType;

    private String cmdString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    public DataAccessDB(String dbName)
    {
        this.dbName = dbName;
    }

    public void open(String dbPath) {
        String url;
        try {
            // Setup for HSQL
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            c1 = DriverManager.getConnection(url, "SA", "");
            st1 = c1.createStatement();
            st2 = c1.createStatement();
            st3 = c1.createStatement();

            rs2 = st1.executeQuery("select * from recipes");
            if (!rs2.next()) {
                initData();
            }
        }
        catch (Exception error)
        {
            processSQLError(error);
        }
        Log.d("OpenDatabase", "Opened " + dbType + " database " + dbPath);
    }

    public void close()
    {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception error)
        {
            processSQLError(error);
        }
        Log.d("CloseDatabase", "Closed " + dbType + " database " + dbName);
    }

    public List<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = null;
        ArrayList<Ingredient> ingredients = null;
        String recipeName, instructions, ingredientName;
        boolean isDefault;
        int recipeId, ingredientQuantity;

        // get table of recipes
        try {
            cmdString = "SELECT * FROM RECIPES";
            rs2 = st1.executeQuery(cmdString);
        }
        catch (Exception error) {
            processSQLError(error);
        }

        // build list of recipes
        try {
            recipes = new ArrayList<Recipe>();
            ingredients = new ArrayList<Ingredient>();

            while (rs2.next()) {

                recipeId = rs2.getInt("ID");
                recipeName = rs2.getString("NAME");
                instructions = rs2.getString("INSTRUCTIONS");
                isDefault = rs2.getBoolean("IS_DEFAULT");
                ingredients = new ArrayList<>(getRecipeIngredients(recipeId));

                Recipe recipe = new Recipe(recipeId, recipeName, ingredients, instructions, isDefault);

                recipes.add(recipe);
            }
            rs2.close();
        }
        catch (Exception error) {
            result = processSQLError(error);
        }

        return recipes;
    }

    public List<Ingredient> getRecipeIngredients(int recipeId) {
        ArrayList<Ingredient> ingredients = null;
        Ingredient ingredient = null;
        int ingredientId;
        double quantity;
        String unit, name;

        // get all ingredients associated with the recipe
        try {
            cmdString = "select (INGREDIENTID, QUANTITY, UNIT) from RECIPEINGREDIENTS where RECIPEID=" + recipeId + ";";
            rs2 = st1.executeQuery(cmdString);
        } catch (Exception error) {
            processSQLError(error);
        }

        // build the list of ingredients
        try {
            ingredients = new ArrayList<Ingredient>();

            while (rs2.next()) {
                ingredientId = rs2.getInt("INGREDIENTID");
                quantity = rs2.getDouble("QUANTITY");
                unit = rs2.getString("UNIT");
                name = null;

                cmdString = "select (NAME) from INGREDIENTS where ID=" +ingredientId + ";";
                rs3 = st1.executeQuery(cmdString);
                if (rs3.next()) {
                    name = rs3.getString("NAME");
                }

                ingredient = new Ingredient(name, factory(unit, quantity));
                ingredients.add(ingredient);
            }

            rs2.close();
        } catch (Exception error) {
            processSQLError(error);
        }
        return ingredients;
    }

    private IUnit factory(String unit, double quantity) {
        IUnit result = null;

        if(unit.equalsIgnoreCase("Cup"))
            result = new Cup(quantity);
        else if(unit.equalsIgnoreCase("Count"))
            result = new Count(quantity);
        else if(unit.equalsIgnoreCase("Gram"))
            result = new Gram(quantity);
        else if(unit.equalsIgnoreCase("Millilitre"))
            result = new Millilitre(quantity);
        else if(unit.equalsIgnoreCase("Ounce"))
            result = new Ounce(quantity);
        else if(unit.equalsIgnoreCase("Tablespoon"))
            result = new Tablespoon(quantity);
        else if(unit.equalsIgnoreCase("Teaspoon"))
            result = new Teaspoon(quantity);

        return result;
    }

    public String processSQLError(Exception e)
    {
        String result = "*** SQL Error: " + e.getMessage();

        // Remember, this will NOT be seen by the user!
        e.printStackTrace();

        return result;
    }

    /**
     * If no data is present, populate the database with initial data
     */
    private void initData() {
        try {
            st1 = c1.createStatement();

            for ( String script : populateScript) {
                st2.executeQuery(script);
            }
        } catch (Exception error) {
            processSQLError(error);
        }
    }

    /**
     * this field contains sql script to populate the DB with initial data
     * this exact script inside the Recipes.script file itself was producing errors with keys, so we will do it separately here.
     */
    private static String[] populateScript = {
            "INSERT INTO RECIPES (ID, NAME, INSTRUCTIONS, IS_DEFAULT) VALUES(NULL, 'Grilled Basil Chicken',\n"
                    + "    'After washing basil and tomatoes, blot them dry with clean paper towel.\\n'\n"
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
            "INSERT INTO INGREDIENTS VALUES (NULL, 'Balsamic Vinegar');",
            "INSERT INTO INGREDIENTS VALUES (NULL, 'Balsamic Vinegar')\n"
                    + "INSERT INTO INGREDIENTS VALUES (NULL, 'Basil Leaves')\n"
                    + "INSERT INTO INGREDIENTS VALUES (NULL, 'Olive Oil')\n"
                    + "INSERT INTO INGREDIENTS VALUES (NULL, 'Plum Tomatoes')\n"
                    + "INSERT INTO INGREDIENTS VALUES (NULL, 'Boneless Skinless Chicken Breast')",
            "INSERT INTO RECIPEINGREDIENTS VALUES (0, 0, 0.75, 'CUP')\n"
                    + "INSERT INTO RECIPEINGREDIENTS VALUES (0, 1, 0.25, 'BOOGA')\n"
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
}
