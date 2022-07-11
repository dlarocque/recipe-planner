package com.example.recipe_planner.persistence;

import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

public interface DataAccess {

    void open(String string);

    void close();

    List<Recipe> getRecipes();

    List<Ingredient> getRecipeIngredients(int recipeId);

    boolean deleteRecipe(int recipeId);
}
