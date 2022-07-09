package com.example.recipe_planner.persistence;

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
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " +dbType +" database " +dbPath);
    }

    public void close()
    {
        try
        {	// commit all changes to the database
            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    public List<Recipe> getRecipes(){
        ArrayList<Recipe> recipes = null;
        ArrayList<Ingredient> ingredients = null;
        String recipe_name, instructions, ingredient_name, unit;
        float quantity;
        boolean isDefault;

        try {
//            cmdString = "SELECT " +
//                    "RECIPES.NAME as RECIPENAME," +
//                    "RECIPES.INSTRUCTIONS," +
//                    "RECIPEINGREDIENTS.NAME as INGREDIENTNAME," +
//                    "RECIPEINGREDIENTS.QUANTITY," +
//                    "RECIPEINGREDIENTS.UNIT " +
//                    "FROM RECIPES " +
//                    "LEFT JOIN RECIPEINGREDIENTS ON " +
//                    "RECIPES.ID = RECIPEINGREDIENTS.RECIPE";
            cmdString = "SELECT * FROM RECIPES";
            System.out.println(cmdString);
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }

        try
        {
            recipes = new ArrayList<Recipe>();
            ingredients = new ArrayList<Ingredient>();

            while (rs2.next())
            {
                recipe_name = rs2.getString("RECIPENAME");
                instructions = rs2.getString("INSTRUCTIONS");
                ingredient_name = rs2.getString("INGREDIENTNAME");
                unit = rs2.getString("UNIT");
                quantity = rs2.getFloat("QUANTITY");

                Ingredient i = new Ingredient(ingredient_name, factory(unit, quantity));

                ingredients.add(i);

                Recipe recipe = new Recipe(recipe_name, ingredients, instructions);

                recipes.add(recipe);
            }
            rs2.close();
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return recipes;
    }

    private IUnit factory(String unit, float quantity){
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
}
