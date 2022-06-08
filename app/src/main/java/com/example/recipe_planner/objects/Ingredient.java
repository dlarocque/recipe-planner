package com.example.recipe_planner.objects;

public class Ingredient {
    private String name;
    private double amount;
    private Unit unit;

    public Ingredient(String name, double amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public Unit getUnit() {
        return this.unit;
    }

    // setters
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }
}
