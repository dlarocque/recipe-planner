package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.business.AccessSchedule;
import com.example.recipe_planner.objects.DaySchedule;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.IUnit;
import com.example.recipe_planner.objects.measurements.Unit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A {@link Fragment} representing a list of Recipes.
 */
public class RecipeList extends Fragment
        implements RecipeRecyclerViewAdapter.OnRecipeClickListener,
        RecipeRecyclerViewAdapter.OnScheduleRecipeClickListener {

    public static final String ARG_RECIPE_ID = "recipeId";
    private final String TAG = this.getClass().getSimpleName();
    private AccessRecipes accessRecipes;
    private AccessSchedule accessSchedule;
    private AccessIngredients accessIngredients;

    private List<Recipe> recipes;

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
        accessIngredients = new AccessIngredients();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recipes = accessRecipes.getRecipes();

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(
                new RecipeRecyclerViewAdapter(recipes, this, this));

        Button createRecipeButton = (Button) view.findViewById(R.id.NewRecipe);
        createRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( view.getContext() );
                alertDialogBuilder.setTitle( "Create a New Recipe" );

                final EditText name = new EditText(view.getContext());
                final EditText ingredients = new EditText(view.getContext());
                final EditText instructions = new EditText(view.getContext());

                name.setHint( "Recipe Name" );
                name.setGravity( Gravity.CENTER );
                name.setBackgroundResource( R.drawable.editbox);

                ingredients.setHint( "Ingredient: Name,Unit,Amount;...;..." );
                ingredients.setGravity( Gravity.CENTER );
                ingredients.setBackgroundResource( R.drawable.editbox);

                instructions.setHint( "Instructions" );
                instructions.setGravity( Gravity.CENTER );
                instructions.setBackgroundResource( R.drawable.editbox);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins( 20,20,20,20);

                LinearLayout lp=new LinearLayout( view.getContext() );
                lp.setOrientation( LinearLayout.VERTICAL );

                lp.addView( name ,layoutParams);
                lp.addView( ingredients,layoutParams );
                lp.addView( instructions ,layoutParams);
                alertDialogBuilder.setView( lp );

                alertDialogBuilder.setPositiveButton( "Create", (dialogInterface, i) -> {

                    String nameString = name.getText().toString().trim();
                    String ingredientString = ingredients.getText().toString().trim();
                    String instructionString = instructions.getText().toString().trim();

                    AlertDialog.Builder alert = new AlertDialog.Builder( view.getContext() );

                    if (validateRecipeData(nameString, ingredientString, instructionString)) {

                        ArrayList<Ingredient> inputIngredients = parseIngredientInput(ingredientString);

                        if (insertUserRecipe(nameString, inputIngredients, instructionString)) {
                            alert.setTitle("Recipe Created Successfully");
                        }
                        else
                            alert.setTitle( "Recipe Insertion Failed" );

                    } else {
                        alert.setTitle( "Error creating recipe (try checking the format of ingredient input)" );
                    }

                    AlertDialog alertDialog2 = alert.create();
                    alertDialog2.show();
                });

                alertDialogBuilder.setNegativeButton( "Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        SearchView simpleSearchView = (SearchView) view.findViewById(R.id.SearchRecipes);
        simpleSearchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        List<Recipe> results = accessRecipes.getRecipesWithPartialName(query);
                        ImageView emptyRecipeListView = view.findViewById(R.id.emptySearch);
                        if (results.isEmpty()) {
                            recyclerView.setVisibility(View.INVISIBLE);
                            emptyRecipeListView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyRecipeListView.setVisibility(View.INVISIBLE);
                            recyclerView.setAdapter(
                                    new RecipeRecyclerViewAdapter(
                                            results, RecipeList.this, RecipeList.this));
                            recipes = results;
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return onQueryTextSubmit(newText);
                    }
                });

        return view;
    }

    public boolean validateRecipeData(String name, String ingredients, String instructions) {
        ArrayList<Ingredient> inputIngredients = new ArrayList<>();
        boolean validName = false;
        boolean validInstructions = false;
        boolean validIngredients = false;

        if (name != null) { validName = true; }

        if (instructions != null) { validInstructions = true; }

        if (ingredients != null) {
            inputIngredients = parseIngredientInput(ingredients);
            if (inputIngredients.size() >= 1) {
                validIngredients = true;
            }
        }

        return validName && validInstructions && validIngredients;
    }

    public ArrayList<Ingredient> parseIngredientInput(String ingredients) {
        ArrayList<Ingredient> inputIngredients = new ArrayList<>();

        String[] singleIngredients;
        String[] ingredientComponents;

        try {
            singleIngredients = ingredients.split(";");

            if (singleIngredients.length >= 1) {
                for (String s : singleIngredients) {
                    ingredientComponents = s.split(",");

                    if (ingredientComponents.length == 3) {
                        IUnit unit = unitFactory(ingredientComponents[1], Double.parseDouble(ingredientComponents[2]));
                        inputIngredients.add(new Ingredient(ingredientComponents[1], unit));
                    }
                }
            }
        } catch (Exception e) {
            Log.d("parseIngredientInput", "Error parsing ingredient input");
        }

        return inputIngredients;
    }

    public boolean insertUserRecipe(String recipeName, ArrayList<Ingredient> userIngredients, String userInstructions) {
        Recipe recipe;
        List<Recipe> recipes;
        boolean ingredientInsert = false;
        boolean recipeInsert = false;
        boolean recipeIngredientInsert = false;

        if (userIngredients != null) {
            for (Ingredient i : userIngredients) {
                if(!accessIngredients.addIngredient(i))
                    break;
            }
            ingredientInsert = true;
        }

        if (ingredientInsert) {
            recipe = new Recipe(-1, recipeName, userIngredients, userInstructions);

            recipeInsert = accessRecipes.addRecipe(recipe);

            if (recipeInsert) {
                recipes = accessRecipes.getRecipesWithPartialName(recipeName);

                if (recipes != null) {
                    for (Ingredient i : userIngredients) {
                        if(!accessRecipes.addRecipeIngredient(i, recipes.get(0)))
                            break;
                    }

                    recipeIngredientInsert = true;
                }
            }
        }

        return ingredientInsert && recipeInsert && recipeIngredientInsert;
    }

    private IUnit unitFactory(String unitName, double quantity) {
        IUnit unit = null;

        switch (unitName) {
            case "CUP":
                unit = new ConvertibleUnit(Unit.CUP, quantity);
                break;
            case "ML":
                unit = new ConvertibleUnit(Unit.ML, quantity);
                break;
            case "GRAM":
                unit = new ConvertibleUnit(Unit.GRAM, quantity);
                break;
            case "OUNCE":
                unit = new ConvertibleUnit(Unit.OUNCE, quantity);
                break;
            case "TSP":
                unit = new ConvertibleUnit(Unit.TSP, quantity);
                break;
            case "TBSP":
                unit = new ConvertibleUnit(Unit.TBSP, quantity);
                break;
            default:
                unit = new Count(quantity);
                break;
        }
        return unit;
    }

    @Override
    public void onRecipeClick(int positionInList, View view) {
        Log.d("RecipeList", "Recipe " + positionInList + " clicked");
        // Navigate to the recipe view for the recipe that was clicked
        Bundle bundle = new Bundle();
        Recipe clickedRecipe = recipes.get(positionInList);
        bundle.putInt(ARG_RECIPE_ID, clickedRecipe.getId());
        Navigation.findNavController(view).navigate(R.id.action_recipeList_to_recipeView, bundle);
    }

    @Override
    public void onScheduleRecipeClick(int positionInList, View view) {
        Log.d(TAG, "Scheduling Button for recipe " + positionInList + " clicked");
        Bundle bundle = new Bundle();
        Recipe clickedRecipe = recipes.get(positionInList);
        bundle.putInt(ARG_RECIPE_ID, clickedRecipe.getId());
        DialogFragment df = new DatePickerFragment(bundle);
        df.show(this.getChildFragmentManager(), TAG);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
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
            alertDialogBuilder.setItems(
                    dialogItems,
                    (dialogInterface, i) -> {
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
