package com.example.recipe_planner.objects.measurements;

public class Ounce implements IMass {
    private static double toGram = 0.03527396;

    private double amount;

    public Ounce(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public double convertToOunce() {
        return this.getAmount();
    }

    @Override
    public double convertToGram() {
        return this.amount * toGram;
    }
}
