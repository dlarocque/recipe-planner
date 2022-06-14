package com.example.recipe_planner.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;

public class EditRecipe extends Fragment {

    private AccessRecipes accessRecipes;

    public EditRecipe() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String editRecipeName = getArguments().getString("name");
        String editRecipeInstruction = accessRecipes.getRecipeInstructions(editRecipeName);

        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        ImageButton back = view.findViewById(R.id.back_to_recipe_from_edit);

        Button submit = view.findViewById(R.id.submit_edit);

        EditText recipeName = (EditText)view.findViewById(R.id.recipe_name_edit);

        recipeName.setText(editRecipeName);

        EditText recipeInstructions = (EditText)view.findViewById(R.id.recipe_instruction_edit);

        recipeInstructions.setText(editRecipeInstruction);

        back.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_editRecipe_to_recipeList3);
            }
        });

        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                String newInstructions = recipeInstructions.getText().toString();
                String newName = recipeName.getText().toString();
                accessRecipes.setRecipeName(editRecipeName, newName);
                accessRecipes.setRecipeInstructions(newInstructions, newName);
                Navigation.findNavController(view).navigate(R.id.action_editRecipe_to_recipeList3);
            }
        });

        return view;
    }
}