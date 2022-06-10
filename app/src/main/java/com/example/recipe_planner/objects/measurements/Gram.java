package com.example.recipe_planner.objects.measurements;

public class Gram implements IMass {
    private static double toOunce = 28.34952;

    private double amount;

    public Gram(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public double convertToOunce() {
        return this.amount * toOunce;
    }

    @Override
    public double convertToGram() {
        return this.getAmount();
    }
}
