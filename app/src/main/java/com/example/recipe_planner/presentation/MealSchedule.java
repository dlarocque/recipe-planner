package com.example.recipe_planner.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A {@link Fragment} representing a {@link DaySchedule} schedule.
 */
public class MealSchedule extends Fragment {
    public static final int DAY_INCREMENT = 1;
    private final String TAG = this.getClass().getSimpleName();
    private final HashMap<DaySchedule.Meal, TextView> mealTextViews;
    private AccessSchedule accessSchedule;
    private AccessRecipes accessRecipes;
    private Date selectedDate;
    private TextView dateText;
    private TextView breakfastMealName;
    private TextView lunchMealName;
    private TextView dinnerMealName;
    private ImageButton descheduleBreakfastButton;
    private ImageButton descheduleLunchButton;
    private ImageButton descheduleDinnerButton;

    public MealSchedule() {
        // Required empty public constructor
        this.mealTextViews = new HashMap<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessSchedule = new AccessSchedule();
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_schedule, container, false);

        selectedDate = Calendar.getInstance().getTime();
        dateText = view.findViewById(R.id.dateText);
        breakfastMealName = view.findViewById(R.id.breakfastRecipeName);
        lunchMealName = view.findViewById(R.id.lunchRecipeName);
        dinnerMealName = view.findViewById(R.id.dinnerRecipeName);
        descheduleBreakfastButton = view.findViewById(R.id.descheduleBreakfastButton);
        descheduleLunchButton = view.findViewById(R.id.descheduleLunchButton);
        descheduleDinnerButton = view.findViewById(R.id.descheduleDinnerButton);

        this.mealTextViews.put(DaySchedule.Meal.BREAKFAST, breakfastMealName);
        this.mealTextViews.put(DaySchedule.Meal.LUNCH, lunchMealName);
        this.mealTextViews.put(DaySchedule.Meal.DINNER, dinnerMealName);

        ImageButton previousDayButton = view.findViewById(R.id.previousDayButton);
        previousDayButton.setOnClickListener(
                previousDayClick -> {
                    incrementDay(-DAY_INCREMENT);
                    updateView();
                });

        ImageButton nextDayButton = view.findViewById(R.id.nextDayButton);
        nextDayButton.setOnClickListener(
                nextDayClick -> {
                    incrementDay(DAY_INCREMENT);
                    updateView();
                });

        updateView();
        return view;
    }

    /**
     * Update all of the UI elements in the fragment.
     */
    private void updateView() {
        updateDate();
        updateDaySchedule();
        updateDeleteButtons();
    }

    private void updateDaySchedule() {
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(selectedDate);
        final String NO_MEAL_SCHEDULED = "No Meal Scheduled";
        Log.d(TAG, "Updated day schedule to day " + CalendarUtils.formattedDate(selectedDate));

        Recipe scheduledRecipe;
        TextView mealTextView;

        // For all meals, display the current recipe name or the default string if there is no
        // recipe scheduled
        for (DaySchedule.Meal meal : DaySchedule.Meal.values()) {
            if ((mealTextView = this.mealTextViews.get(meal)) != null) {
                scheduledRecipe = daySchedule.getMeal(meal);
                if (recipeExists(scheduledRecipe)) {
                    mealTextView.setText(scheduledRecipe.getName());
                } else {
                    mealTextView.setText(NO_MEAL_SCHEDULED);
                }
            }
        }
    }

    private boolean recipeExists(Recipe recipe) {
        return recipe != null && this.accessRecipes.getRecipe(recipe.getId()) != null;
    }

    private void updateDate() {
        dateText.setText(CalendarUtils.formattedDate(selectedDate));
        Log.d(TAG, "Updated date to " + CalendarUtils.formattedDate(selectedDate));
    }

    private void updateDeleteButtons() {
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(selectedDate);

        updateDeleteButton(
                descheduleBreakfastButton, daySchedule, selectedDate, DaySchedule.Meal.BREAKFAST);
        updateDeleteButton(
                descheduleLunchButton, daySchedule, selectedDate, DaySchedule.Meal.LUNCH);
        updateDeleteButton(
                descheduleDinnerButton, daySchedule, selectedDate, DaySchedule.Meal.DINNER);
    }

    private void updateDeleteButton(
            ImageButton deleteButton, DaySchedule daySchedule, Date date, DaySchedule.Meal meal) {
        boolean isEnabled = recipeExists(daySchedule.getMeal(meal));
        deleteButton.setEnabled(isEnabled);
        if (isEnabled) {
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }

        deleteButton.setOnClickListener(descheduleMeal(date, meal));
    }

    private View.OnClickListener descheduleMeal(Date date, DaySchedule.Meal meal) {
        return clickListener -> {
            accessSchedule.descheduleMeal(date, meal);
            updateView();
        };
    }

    private void incrementDay(int increment) {
        Log.d(TAG, "Incrementing day by " + increment);
        selectedDate = CalendarUtils.incrementDay(selectedDate, increment);
        updateDate();
    }
}
