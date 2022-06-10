package com.example.recipe_planner.objects.measurements;

public class Cup implements IVolume {
    // see IVolume.java declaration for unit specifics
    private static final double toTbsp = 16;
    private static final double toTsp = 48;
    private static final double toMl = 236.5875;

    private final double amount;

    public Cup(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    @Override
    public double convertToCup() {
        return this.getAmount();
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
        return this.amount * toTsp;
    }
}
