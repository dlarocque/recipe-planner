package com.example.recipe_planner.objects.measurements;

public class Teaspoon implements IVolume {
    // see IVolume.java declaration for unit specifics
    private static double toTbsp = 0.3333333;
    private static double toMl = 4.928906;
    private static double toCup = 0.02083333;

    private double amount;

    public Teaspoon(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    @Override
    public double convertToCup() {
        return this.amount * toCup;
    }

    @Override
    public double convertToMl() {
        return this.amount * toMl;
    }

    @Override
    public double convertToTablespoon() {
        return this.amount * toTbsp;
    }

    @Override
    public double convertToTeaspoon() {
        return this.getAmount();
    }
}
