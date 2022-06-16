package com.example.recipe_planner.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.recipe_planner.R;

/** A simple {@link Fragment} subclass. */
public class MealSchedule extends Fragment {

    public MealSchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_schedule, container, false);
    }
}
