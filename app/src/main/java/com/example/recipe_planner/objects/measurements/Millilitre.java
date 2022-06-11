package com.example.recipe_planner.objects.measurements;

public class Millilitre implements IVolume {
    // see IVolume.java declaration for unit specifics
    private static final double toTbsp = 0.06762826;
    private static final double toTsp = 0.2028848;
    private static final double toCup = 0.004226766249;

    private final double amount;

    public Millilitre(double amount) {
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
        return this.getAmount();
    }

    @Override
    public double convertToTablespoon() {
        return this.amount * toTbsp;
    }

    @Override
    public double convertToTeaspoon() {
        return this.amount * toTsp;
    }
}
