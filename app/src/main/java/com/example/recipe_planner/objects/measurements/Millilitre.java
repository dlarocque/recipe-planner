package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Millilitre implements IConvertibleUnit {

    private static final Map<Unit, Double> CONVERSION;

    static {
        Map<Unit, Double> temp = new HashMap<>();
        temp.put(Unit.TBSP, 0.0667);
        temp.put(Unit.TSP, 0.2);
        temp.put(Unit.CUP, 0.0042);
        CONVERSION = Collections.unmodifiableMap(temp);
    }

    private final double amount;

    public Millilitre(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    @Override
    public double convertTo(Unit unit) {
        if (CONVERSION.get(unit) != null) {
            return this.amount * CONVERSION.get(unit);
        } else {
            throw new UnsupportedOperationException(
                    "Conversion from Millilitre to " + unit + "is not supported.");
        }
    }
}
