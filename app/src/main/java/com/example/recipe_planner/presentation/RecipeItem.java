package com.example.recipe_planner.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;

import java.util.ArrayList;
import java.util.List;

/** A fragment representing a list of Recipes. */
public class RecipeItem extends Fragment implements RecipeRecyclerViewAdapter.OnEditListener{

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 1;
    private AccessRecipes accessRecipes;
    private ImageButton edit;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public RecipeItem() {}

    @SuppressWarnings("unused")
    public static RecipeItem newInstance(int columnCount) {
        RecipeItem fragment = new RecipeItem();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            recyclerView.setAdapter(new RecipeRecyclerViewAdapter(accessRecipes.getRecipes(), this));
        }
        return view;
    }

    @Override
    public void onEditClick(String name, View view) {
        Bundle bundle = new Bundle();
        List<Recipe> recipes = accessRecipes.getRecipesWithName(name);
        bundle.putString("name", recipes.get(0).getName());
        Navigation.findNavController(view).navigate(R.id.action_recipeList_to_editRecipe, bundle);
    }
}
