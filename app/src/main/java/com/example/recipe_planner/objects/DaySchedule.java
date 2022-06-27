package com.example.recipe_planner.objects;

public class DaySchedule {
    private Recipe breakfast;
    private Recipe lunch;
    private Recipe dinner;

    public DaySchedule() {
    }

    public DaySchedule(Recipe breakfast, Recipe lunch, Recipe dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getBreakfastName() {
        return breakfast.getName();
    }

    public void setBreakfast(Recipe breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunchName() {
        return lunch.getName();
    }

    public void setLunch(Recipe lunch) {
        this.lunch = lunch;
    }

    public String getDinnerName() {
        return dinner.getName();
    }

    public void setDinner(Recipe dinner) {
        this.dinner = dinner;
    }

    public boolean breakfastIsScheduled() {
        return this.breakfast != null;
    }

    public boolean lunchIsScheduled() {
        return this.lunch != null;
    }

    public boolean dinnerIsScheduled() {
        return this.dinner != null;
    }

}
