package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cup implements IConvertibleUnit {

    private static final Map<Unit, Double> CONVERSION;

    static {
        Map<Unit, Double> temp = new HashMap<>();
        temp.put(Unit.TBSP, 16.0);
        temp.put(Unit.TSP, 48.0);
        temp.put(Unit.ML, 237.0);
        CONVERSION = Collections.unmodifiableMap(temp);
    }

    private final double amount;

    public Cup(double amount) {
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
                    "Conversion from Cup to " + unit + "is not supported.");
        }
    }
}
