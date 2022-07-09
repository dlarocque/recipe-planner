package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Tablespoon implements IConvertibleUnit {

    private static final Map<Unit, Double> CONVERSION;

    static {
        Map<Unit, Double> temp = new HashMap<>();
        temp.put(Unit.TSP, 3.0);
        temp.put(Unit.ML, 15.0);
        temp.put(Unit.CUP, 0.0625);
        CONVERSION = Collections.unmodifiableMap(temp);
    }

    private final double amount;

    public Tablespoon(double amount) {
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
                    "Conversion from Tablespoon to " + unit + "is not supported.");
        }
    }
}
