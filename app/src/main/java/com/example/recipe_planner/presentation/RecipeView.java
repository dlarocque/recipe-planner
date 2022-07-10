package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;

import java.util.ArrayList;

/**
 * {@link Fragment} that displays a single {@link Recipe}, specifically its name and instructions
 */
public class RecipeView extends Fragment {

    public static final String RECIPE_POSITION_IN_LIST = "positionInList";

    private Recipe recipe;
    private EditText recipeName;
    private EditText recipeInstructions;
    TextWatcher textWatcher =
            new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Required override, stub function
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Required override, stub function
                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateRecipe(); // When there is a change made to editable test, we want to
                    // update the recipe objects to reflect those changes.
                }
            };
    private ArrayList<Recipe> recipeList;
    private AccessRecipes accessRecipes;

    public RecipeView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.accessRecipes = new AccessRecipes();
        recipeList = new ArrayList<>(this.accessRecipes.getRecipes());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert (getArguments() != null);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);

        // Retrieve recipe data to display
        int positionInRecipeList = getArguments().getInt(RecipeList.ARG_RECIPE_ID);
        this.recipe = recipeList.get(positionInRecipeList);
        this.recipeName = view.findViewById(R.id.recipe_name_edit);
        this.recipeInstructions = view.findViewById(R.id.recipe_instruction_edit);

        // Set up the text boxes and listeners
        recipeName.setText(recipe.getName());
        recipeName.addTextChangedListener(textWatcher);
        recipeInstructions.setText(recipe.getInstructions());
        recipeInstructions.addTextChangedListener(textWatcher);

        // Set up the delete button to delete the recipe upon confirmation of a dialog.
        // Once the recipe is deleted, we navigate back to the recipe list.
        ImageButton button = view.findViewById(R.id.deleteButton);
        button.setOnClickListener(
                clickedView -> {
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(clickedView.getContext());
                    alertDialogBuilder.setTitle("Delete Recipe");
                    alertDialogBuilder.setMessage("Do you want to delete this recipe?");
                    alertDialogBuilder.setIcon(R.drawable.ic_launcher_delete_foreground);
                    alertDialogBuilder.setPositiveButton(
                            "Yes",
                            (dialog, id) -> {
                                dialog.dismiss();
                                Log.d("RecipeView", "Delete Recipe button clicked");
                                Navigation.findNavController(clickedView)
                                        .navigate(R.id.action_recipeView_to_recipeList);
                                // delete the recipe and refresh the list
                                accessRecipes.deleteRecipe(recipe.getId());
                                recipeList.remove(recipe);
                            });
                    alertDialogBuilder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                });
        ImageButton editIngredients = view.findViewById(R.id.editIngredients);
        editIngredients.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt(RECIPE_POSITION_IN_LIST, positionInRecipeList);
            Navigation.findNavController(view1).navigate(R.id.action_recipeView_to_ingredientEdit, bundle);
        });

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.ingredientList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(
                new IngredientRecyclerViewAdapter(recipe.getIngredients()));

        return view;
    }

    /**
     * Saves the changes made to the recipe, called when the recipes are edited.
     */
    public void updateRecipe() {
        String newInstructions = this.recipeInstructions.getText().toString();
        String newName = this.recipeName.getText().toString();
        this.recipe.setName(newName);
        this.recipe.setInstructions(newInstructions);
    }
}
