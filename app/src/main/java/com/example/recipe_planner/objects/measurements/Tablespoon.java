package com.example.recipe_planner.objects.measurements;

public class Tablespoon implements IVolume {
    // see IVolume.java declaration for unit specifics
    private static double toTsp = 3;
    private static double toMl = 14.78672;
    private static double toCup = 0.0625;

    private double amount;

    public Tablespoon(double amount) {
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
        return this.getAmount();
    }

    @Override
    public double convertToTeaspoon() {
        return this.amount * toTsp;
    }
}
