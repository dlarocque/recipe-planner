package com.example.recipe_planner.objects.measurements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Teaspoon implements IConvertibleUnit {

    private static final Map<Unit, Double> CONVERSION;

    static {
        Map<Unit, Double> temp = new HashMap<>();
        temp.put(Unit.TBSP, 0.3333);
        temp.put(Unit.ML, 5.0);
        temp.put(Unit.CUP, 0.0208);
        CONVERSION = Collections.unmodifiableMap(temp);
    }

    private final double amount;

    public Teaspoon(double amount) {
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
                    "Conversion from Teaspoon to " + unit + "is not supported.");
        }
    }
}
