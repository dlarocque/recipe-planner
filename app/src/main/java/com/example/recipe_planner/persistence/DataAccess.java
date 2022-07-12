package com.example.recipe_planner.persistence;

import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.Date;
import java.util.List;

public interface DataAccess {

    void open(String string);

    void close();

    Recipe getRecipe(int recipeId);

    List<Recipe> getRecipes();

    List<Ingredient> getRecipeIngredients(int recipeId);

    boolean deleteRecipe(int recipeId);

//    boolean deleteIngredient(int recipeID, int ingredientID);
//
//    boolean addIngredient(int recipeID, String ingredientName, String unit, Double quantity);
//
//    boolean updateIngredientName(int recipeID, String ingredientName);
//
//    boolean updateIngredientQuantity(int recipeID, Double quantity);
//
//    boolean updateIngredientUnit(int recipeID, String unit);

    DaySchedule getDaySchedule(Date date);

    void initializeDaySchedule(Date date);

    void setDayScheduleMeal(Date date, DaySchedule.Meal meal, Recipe recipe);

    void setDayScheduleMealNull(Date date, DaySchedule.Meal meal);
}
