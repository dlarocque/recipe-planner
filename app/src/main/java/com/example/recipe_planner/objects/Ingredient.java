package com.example.recipe_planner.objects;

import com.example.recipe_planner.objects.measurements.IUnit;

public class Ingredient {
    private final String name;
    private IUnit quantity;

    public Ingredient(String name, IUnit amount) {
        this.name = name;
        this.quantity = amount;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.quantity.getAmount();
    }

    // setters
    public void setAmount(IUnit newAmount) {
        this.quantity = newAmount;
    }

    public IUnit getUnit() {
        return this.quantity;
    }

    @Override
    public boolean equals(Object other) {
        Ingredient i = (Ingredient) other;
        return this.name.equals(i.getName())
                && Double.compare(this.getAmount(), i.getAmount()) == 0;
    }
}
