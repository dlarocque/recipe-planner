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
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * A {@link Fragment} representing a {@link DaySchedule} schedule.
 */
public class MealSchedule extends Fragment {
    public static final int DAY_INCREMENT = 1;
    private final String TAG = this.getClass().getSimpleName();
    private AccessSchedule accessSchedule;
    private Date selectedDate;
    private TextView dateText;
    private TextView breakfastMealName;
    private TextView lunchMealName;
    private TextView dinnerMealName;

    public MealSchedule() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessSchedule = new AccessSchedule();
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

        ImageButton previousDayButton = view.findViewById(R.id.previousDayButton);
        previousDayButton.setOnClickListener(previousDayClick -> {
            incrementDay(-DAY_INCREMENT);
            updateDaySchedule();
        });

        ImageButton nextDayButton = view.findViewById(R.id.nextDayButton);
        nextDayButton.setOnClickListener(nextDayClick -> {
            incrementDay(DAY_INCREMENT);
            updateDaySchedule();
        });

        updateDate();
        updateDaySchedule();
        return view;
    }

    private void updateDaySchedule() {
        DaySchedule daySchedule = accessSchedule.getDayScheduleOrDefault(selectedDate);
        final String NO_MEAL_SCHEDULED = "No Meal Scheduled";
        Log.d(TAG, "Updated day schedule to day " + CalendarUtils.formattedDate(selectedDate));

        // Display the current recipe name or the default string if there is no recipe scheduled
        if (daySchedule.breakfastIsScheduled()) {
            breakfastMealName.setText(accessSchedule.getDayScheduleOrDefault(selectedDate).getBreakfastName());
        } else {
            breakfastMealName.setText(NO_MEAL_SCHEDULED);
        }
        if (daySchedule.lunchIsScheduled()) {
            lunchMealName.setText(accessSchedule.getDayScheduleOrDefault(selectedDate).getLunchName());
        } else {
            lunchMealName.setText(NO_MEAL_SCHEDULED);
        }
        if (daySchedule.dinnerIsScheduled()) {
            dinnerMealName.setText(accessSchedule.getDayScheduleOrDefault(selectedDate).getDinnerName());
        } else {
            dinnerMealName.setText(NO_MEAL_SCHEDULED);
        }
    }

    private void updateDate() {
        dateText.setText(CalendarUtils.formattedDate(selectedDate));
        Log.d(TAG, "Updated date to " + CalendarUtils.formattedDate(selectedDate));
    }

    private void incrementDay(int increment) {
        Log.d(TAG, "Incrementing day by " + increment);
        selectedDate = CalendarUtils.incrementDay(selectedDate, increment);
        updateDate();
    }
}
