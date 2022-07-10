package com.example.recipe_planner.business;

import com.example.recipe_planner.application.Services;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccessStub;

import java.util.List;

public class AccessRecipes {
    private final DataAccessStub dataAccess;

    public AccessRecipes() {
        dataAccess = Services.getDataAccess();
    }

    public Recipe getRecipeAt(int index) {
        return dataAccess.getRecipeAt(index);
    }

    public List<Recipe> getRecipes() {
        return dataAccess.getRecipes();
    }

    public Recipe getRandomRecipe() {
        return dataAccess.getRandomRecipe();
    }

    public List<Recipe> getRecipesWithIngredientName(String ingredientName) {
        return dataAccess.getRecipesWithIngredientName(ingredientName);
    }

    public Recipe getRecipeWithName(String recipeName) {
        return dataAccess.getRecipeWithName(recipeName);
    }

    public List<Recipe> getRecipesWithName(String recipeName) {
        return dataAccess.getRecipesWithName(recipeName);
    }

    public void insertRecipe(Recipe recipe) {
        dataAccess.insertRecipe(recipe);
    }

    public boolean insertRecipes(List<Recipe> recipes) {
        return dataAccess.insertRecipes(recipes);
    }

    public boolean deleteRecipe(Recipe recipe) {
        return dataAccess.deleteRecipe(recipe);
    }

    public String getRecipeInstructions(String recipeName) {
        return dataAccess.getRecipeInstructions(recipeName);
    }

    public void setRecipeName(String recipeName, String editRecipe) {
        dataAccess.setRecipeName(recipeName, editRecipe);
    }

    public void setRecipeInstructions(String recipeInstructions, String recipeName) {
        dataAccess.setRecipeInstructions(recipeInstructions, recipeName);
    }
}
