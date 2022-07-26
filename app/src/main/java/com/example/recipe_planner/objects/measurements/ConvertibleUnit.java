package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConvertibleUnit implements IConvertibleUnit {
    private static final Map<Unit, HashMap> CONVERSION;

    /**
     * Initialize the conversion values. We opted for a single-class hierarchy based on feedback,
     * and decided to store conversion values statically within the class to avoid tightly coupling
     * our domain objects and database.
     */
    static {
        Map<Unit, HashMap> parent = new HashMap<>();
        Map<Unit, Double> cup = new HashMap<>();
        cup.put(Unit.TBSP, 16.0);
        cup.put(Unit.TSP, 48.0);
        cup.put(Unit.ML, 237.0);
        parent.put(Unit.CUP, new HashMap<>(Collections.unmodifiableMap(cup)));

        Map<Unit, Double> gram = new HashMap<>();
        gram.put(Unit.OUNCE, 0.0357);
        parent.put(Unit.GRAM, new HashMap<>(Collections.unmodifiableMap(gram)));

        Map<Unit, Double> millilitre = new HashMap<>();
        millilitre.put(Unit.TBSP, 0.0667);
        millilitre.put(Unit.TSP, 0.2);
        millilitre.put(Unit.CUP, 0.0042);
        parent.put(Unit.ML, new HashMap<>(Collections.unmodifiableMap(millilitre)));

        Map<Unit, Double> ounce = new HashMap<>();
        ounce.put(Unit.GRAM, 28.0);
        parent.put(Unit.OUNCE, new HashMap<>(Collections.unmodifiableMap(ounce)));

        Map<Unit, Double> tablespoon = new HashMap<>();
        tablespoon.put(Unit.TSP, 3.0);
        tablespoon.put(Unit.ML, 15.0);
        tablespoon.put(Unit.CUP, 0.0625);
        parent.put(Unit.TBSP, new HashMap<>(Collections.unmodifiableMap(tablespoon)));

        Map<Unit, Double> teaspoon = new HashMap<>();
        teaspoon.put(Unit.TBSP, 0.3333);
        teaspoon.put(Unit.ML, 5.0);
        teaspoon.put(Unit.CUP, 0.0208);
        parent.put(Unit.TSP, new HashMap<>(Collections.unmodifiableMap(teaspoon)));

        CONVERSION = Collections.unmodifiableMap(parent);
    }

    private double amount;
    private Unit unit;

    public ConvertibleUnit(Unit unit, double amount) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public Unit getUnit() {
        return this.unit;
    }

    @Override
    public double convertTo(Unit newUnit) {
        // 1. grab conversion factor, update amount
        if (CONVERSION.containsKey(this.unit)) {
            Map factors = CONVERSION.get(this.unit);
            if (factors.containsKey(newUnit)) {
                this.amount = this.amount * (double) factors.get(newUnit);
                this.unit = newUnit;
                return this.amount;
            }
        }
        throw new UnsupportedOperationException("Conversion not supported");
    }

    @Override
    public boolean isWeight() {
        return this.unit == Unit.GRAM || this.unit == Unit.OUNCE;
    }

    @Override
    public String toString() {
        try {
            return this.amount + " " + this.unit.toString();
        } catch (NullPointerException invalidUnit) {
            return this.amount + " [invalid unit]";
        }
    }
}
