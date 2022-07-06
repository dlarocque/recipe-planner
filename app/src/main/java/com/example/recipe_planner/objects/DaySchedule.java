package com.example.recipe_planner.objects;

import java.util.HashMap;

/**
 * A daily schedule that contains a {@link Recipe} for each of breakfast, lunch and dinner.
 */
public class DaySchedule {
    private final HashMap<Meal, Recipe> meals = new HashMap<>();

    public DaySchedule() {
    }

    public void setMeal(final Meal MEAL, Recipe recipe) {
        this.meals.put(MEAL, recipe);
    }

    public String getMealName(final Meal MEAL) {
        Recipe meal = this.meals.get(MEAL);
        if (meal == null) {
            return "";
        } else {
            return meal.getName();
        }
    }

    public boolean mealIsScheduled(final Meal MEAL) {
        return this.meals.containsKey(MEAL) && this.meals.get(MEAL) != null;
    }

    public enum Meal {
        BREAKFAST,
        LUNCH,
        DINNER
    }
}
