package com.example.recipe_planner.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;

/**
 * A {@link Fragment} representing a list of Recipes.
 */
public class RecipeList extends Fragment
        implements RecipeRecyclerViewAdapter.OnRecipeClickListener {

    public static final String ARG_POSITION_IN_LIST = "positionInList";
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
        return new RecipeList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new RecipeRecyclerViewAdapter(this.accessRecipes.getRecipes(), this));
        }
        return view;
    }

    @Override
    public void onRecipeClick(int positionInList, View view) {
        Log.d("RecipeList", "Recipe " + positionInList + " clicked");
        // Navigate to the recipe view for the recipe that was clicked
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION_IN_LIST, positionInList);
        Navigation.findNavController(view).navigate(R.id.action_recipeList_to_recipeView, bundle);
    }
}
