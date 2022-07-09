package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Gram implements IConvertibleUnit {

    private static final Map<Unit, Double> CONVERSION;

    static {
        Map<Unit, Double> temp = new HashMap<>();
        temp.put(Unit.OUNCE, 28.0);
        CONVERSION = Collections.unmodifiableMap(temp);
    }

    private final double amount;

    public Gram(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    public double convertTo(Unit unit) {
        if (CONVERSION.get(unit) != null) {
            return this.amount * CONVERSION.get(unit);
        } else {
            throw new UnsupportedOperationException(
                    "Conversion from Gram to " + unit + "is not supported.");
        }
    }
}
