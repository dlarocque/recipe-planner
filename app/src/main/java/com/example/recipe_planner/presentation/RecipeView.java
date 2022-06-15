package com.example.recipe_planner.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;

public class RecipeView extends Fragment {

    private Recipe recipe;
    private EditText recipeName;
    private EditText recipeInstructions;
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
        // Inflate the layout for this fragment
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        this.recipe = accessRecipes.getRecipe(position);

        View view = inflater.inflate(R.layout.fragment_recipe_view, container, false);

        this.recipeName = view.findViewById(R.id.recipe_name_edit);
        this.recipeInstructions = view.findViewById(R.id.recipe_instruction_edit);
        recipeName.setText(recipe.getName());
        recipeInstructions.setText(recipe.getInstructions());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("RecipeView", "Saving state of recipe");

        String newInstructions = this.recipeInstructions.getText().toString();
        String newName = this.recipeName.getText().toString();
        this.recipe.setName(newName);
        this.recipe.setInstructions(newInstructions);
    }

    public void onDeleteClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_recipeView_to_recipeList);
        accessRecipes.deleteRecipe(this.recipe);
    }
}
