package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.List;

public class AccessIngredients {
    private final DataAccess dataAccess;

    public AccessIngredients() {
        dataAccess = Services.getDataAccess();
    }

    public boolean deleteIngredient(int recipeID, String name, double quantity, String unit) {
        return dataAccess.deleteIngredient(recipeID, name, quantity, unit);
    }

    public void updateIngredientQuantity(int recipeID, Double quantity, String ingredientName) {
        dataAccess.updateIngredientQuantity(recipeID, quantity, ingredientName);
    }
}
