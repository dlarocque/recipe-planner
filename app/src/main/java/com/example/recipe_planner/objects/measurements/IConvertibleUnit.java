package com.example.recipe_planner.objects.measurements;

/*
 * Specifies units that are convertible into other units
 */
public interface IConvertibleUnit extends IUnit {
    double convertTo(Unit unit);
}
