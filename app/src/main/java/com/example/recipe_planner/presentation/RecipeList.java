package com.example.recipe_planner.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;

/** A fragment representing a list of Recipes. */
public class RecipeList extends Fragment
        implements RecipeRecyclerViewAdapter.OnRecipeClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 1;
    private AccessRecipes accessRecipes;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public RecipeList() {
        // Mandatory empty constructor
    }

    @SuppressWarnings("unused")
    public static RecipeList newInstance(int columnCount) {
        RecipeList fragment = new RecipeList();
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
            recyclerView.setAdapter(
                    new RecipeRecyclerViewAdapter(accessRecipes.getRecipes(), this));
        }
        return view;
    }

    @Override
    public void onRecipeClick(int position, View view) {
        Log.d("RecipeList", "Recipe " + position + " clicked");
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Navigation.findNavController(view).navigate(R.id.action_recipeList_to_recipeView, bundle);
    }
}
