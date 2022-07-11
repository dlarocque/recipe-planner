package com.example.recipe_planner.persistence;

import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

public interface DataAccess {

    void open(String string);

    void close();

    public List<Recipe> getRecipes();

    public List<Ingredient> getRecipeIngredients(int recipeId);

    public boolean deleteRecipe(int recipeId);
}
