package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.measurements.Unit;
import com.example.recipe_planner.persistence.DataAccess;

public class AccessIngredients {
    private final DataAccess dataAccess;

    public AccessIngredients() {
        dataAccess = Services.getDataAccess();
    }

    public boolean deleteIngredient(int recipeID, String name) {
        return dataAccess.deleteIngredient(recipeID, name);
    }

    public void updateIngredientQuantity(int recipeID, Double quantity, String ingredientName) {
        dataAccess.updateIngredientQuantity(recipeID, quantity, ingredientName);
    }

    public void updateIngredientName(int recipeID, String newName, String ingredientName) {
        dataAccess.updateIngredientName(recipeID, newName, ingredientName);
    }

    public void updateIngredientUnit(
            int recipeID, String ingredientName, Unit newUnit, double newQuantity) {
        dataAccess.updateIngredientUnit(recipeID, ingredientName, newUnit, newQuantity);
    }
}
