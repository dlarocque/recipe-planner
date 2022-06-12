package com.example.recipe_planner.objects.measurements;

public class Count implements IUnit {
    private double amount;

    public Count(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }
}
