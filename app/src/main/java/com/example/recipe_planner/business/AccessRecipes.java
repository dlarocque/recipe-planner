package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.ArrayList;
import java.util.List;

public class AccessRecipes {
    private final DataAccess dataAccess;

    public AccessRecipes() {
        dataAccess = Services.getDataAccess();
    }

    public Recipe getRecipe(int recipeId) {
        return dataAccess.getRecipe(recipeId);
    }

    public List<Recipe> getRecipes() {
        return dataAccess.getRecipes();
    }

    public List<Recipe> getRecipesWithPartialName(String recipePartialName) {
        return dataAccess.getRecipesWithPartialName(recipePartialName);
    }

    public List<Ingredient> getRecipeIngredients(int recipeId) {
        return dataAccess.getRecipeIngredients(recipeId);
    }

    public ArrayList<Recipe> getScheduledRecipes() {
        return dataAccess.getScheduledRecipes();
    }

    public void deleteRecipe(int recipeId) {
        dataAccess.deleteRecipe(recipeId);
    }
}
