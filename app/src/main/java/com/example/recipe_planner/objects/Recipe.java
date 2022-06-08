package com.example.recipe_planner.objects;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String instructions;

    public Recipe(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.instructions = null;
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getInstructions() {
        return this.instructions;
    }

    // public methods
    public void addIngredient(Ingredient newIngredient) {
        this.ingredients.add(newIngredient);
    }
}
