package com.example.recipe_planner.objects.measurements;

public class Tablespoon implements IVolume {
    // see IVolume.java declaration for unit specifics
    private static final double toTsp = 3;
    private static final double toMl = 14.78672;
    private static final double toCup = 0.0625;

    private final double amount;

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
