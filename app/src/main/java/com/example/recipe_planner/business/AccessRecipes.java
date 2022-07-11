package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Main;
import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.List;

public class AccessRecipes {
    private final DataAccess dataAccess;

    public AccessRecipes() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public List<Recipe> getRecipes() {
        return dataAccess.getRecipes();
    }

    public List<Ingredient> getRecipeIngredients(int recipeId) {
        return dataAccess.getRecipeIngredients(recipeId);
    }

    public void deleteRecipe(int recipeId) {
        dataAccess.deleteRecipe(recipeId);
    }

}
