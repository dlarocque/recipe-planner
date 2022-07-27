package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
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
}
