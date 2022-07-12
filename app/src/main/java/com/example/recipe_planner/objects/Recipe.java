package com.example.recipe_planner.objects;

import java.util.ArrayList;

public class Recipe {
    private final ArrayList<Ingredient> ingredients;
    private final boolean isDefault;
    private final int id;
    private String name;
    private String instructions;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.isDefault = false;
    }

    // Use this constructor with isDefault:true when creating our default recipes.
    // The other constructors should be used when a user is creating a recipe.
    public Recipe(
            int id,
            String name,
            ArrayList<Ingredient> ingredients,
            String instructions,
            boolean isDefault) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.isDefault = isDefault;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // public methods
    public void addIngredient(Ingredient newIngredient) {
        this.ingredients.add(newIngredient);
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public int getId() {
        return this.id;
    }
}
