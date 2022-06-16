package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
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

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;

public class RecipeView extends Fragment {

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
                    updateRecipe();
                }
            };
    private AccessRecipes accessRecipes;

    public RecipeView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert(getArguments() != null);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        int positionInRecipeList = getArguments().getInt(RecipeList.ARG_POSITION_IN_LIST);
        this.recipe = accessRecipes.getRecipe(positionInRecipeList);
        this.recipeName = view.findViewById(R.id.recipe_name_edit);
        this.recipeInstructions = view.findViewById(R.id.recipe_instruction_edit);

        recipeName.setText(recipe.getName());
        recipeName.addTextChangedListener(textWatcher);
        recipeInstructions.setText(recipe.getInstructions());

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
                                accessRecipes.deleteRecipe(recipe);
                            });
                    alertDialogBuilder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                });

        return view;
    }

    /**
    Saves the changes made to the recipe, called when the recipes are edited.
     */
    public void updateRecipe() {
        String newInstructions = this.recipeInstructions.getText().toString();
        String newName = this.recipeName.getText().toString();
        this.recipe.setName(newName);
        this.recipe.setInstructions(newInstructions);
    }
}
