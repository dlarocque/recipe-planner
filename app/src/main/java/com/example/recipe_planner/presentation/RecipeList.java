package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Recipe;

import java.util.Calendar;
import java.util.Date;

/**
 * A {@link Fragment} representing a list of Recipes.
 */
public class RecipeList extends Fragment
        implements RecipeRecyclerViewAdapter.OnRecipeClickListener, RecipeRecyclerViewAdapter.OnScheduleRecipeClickListener {

    public static final String ARG_RECIPE_ID = "recipeId";
    private final String TAG = this.getClass().getSimpleName();
    private AccessRecipes accessRecipes;
    private AccessSchedule accessSchedule;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public RecipeList() {
        // Mandatory empty constructor
    }

    @SuppressWarnings("unused")
    public static RecipeList newInstance(int columnCount) {
        return new RecipeList();
    }

    public void showDatePickerDialog(View v) {
        // DialogFragment newFragment = new DatePickerFragment();
        // newFragment.show(getSupportFragmentManager(), "datePicker");
        Log.d("RecipeRecyclerView", "pickerdialogview");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
        accessSchedule = new AccessSchedule();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // Set the adapter
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.recipeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new RecipeRecyclerViewAdapter(accessRecipes.getRecipes(), this, this));
        return view;
    }

    @Override
    public void onRecipeClick(int positionInList, View view) {
        Log.d("RecipeList", "Recipe " + positionInList + " clicked");
        // Navigate to the recipe view for the recipe that was clicked
        Bundle bundle = new Bundle();
        Recipe clickedRecipe = accessRecipes.getRecipes().get(positionInList);
        bundle.putInt(ARG_RECIPE_ID, clickedRecipe.getId()); // TODO fix in view
        Navigation.findNavController(view).navigate(R.id.action_recipeList_to_recipeView, bundle);
    }

    @Override
    public void onScheduleRecipeClick(int positionInList, View view) {
        Log.d(TAG, "Scheduling Button for recipe " + positionInList + " clicked");
        Bundle bundle = new Bundle();
        Recipe clickedRecipe = accessRecipes.getRecipes().get(positionInList);
        bundle.putInt(ARG_RECIPE_ID, clickedRecipe.getId());
        DialogFragment df = new DatePickerFragment(bundle);
        df.show(this.getChildFragmentManager(), TAG);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        AccessRecipes accessRecipes;
        AccessSchedule accessSchedule;
        Bundle bundle;

        public DatePickerFragment(Bundle bundle) {
            accessRecipes = new AccessRecipes();
            accessSchedule = new AccessSchedule();
            this.bundle = bundle;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d("RecipeRecyclerViewAdapter", "Date set to " + year + " " + month + " " + day);
            final CharSequence[] dialogItems = {"Breakfast", "Lunch", "Dinner"};

            // Get the selected date
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date scheduledDate = calendar.getTime();

            // Get the schedule for the selected date
            Recipe scheduledRecipe = accessRecipes.getRecipe(bundle.getInt(ARG_RECIPE_ID));

            // Prompt the user to select a meal, and schedule the meal
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setTitle("Select A Meal");
            alertDialogBuilder.setItems(dialogItems, (dialogInterface, i) -> {
                Log.d("RecipeList", "Selected meal " + dialogItems[i]);
                DaySchedule.Meal selectedMeal = DaySchedule.Meal.values()[i];
                // selectedDateSchedule.setMeal(selectedMeal, scheduledRecipe);
                accessSchedule.setMeal(scheduledDate, selectedMeal, scheduledRecipe);
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
