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
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

public class EditIngredientList extends Fragment{

    private AccessRecipes accessRecipes;

    public EditIngredientList() {
        // Mandatory empty constructor
    }

    public static EditIngredientList newInstance(int columnCount) {return new EditIngredientList();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_ingredients, container, false);

        int positionInRecipeList = getArguments().getInt(RecipeList.ARG_POSITION_IN_LIST);
        Recipe recipe = accessRecipes.getRecipe(positionInRecipeList);

        List<Ingredient> ingredientList = fetchIngredients(recipe);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new EditIngredientListRecyclerViewAdapter(ingredientList));
        }
        return view;
    }

    public List<Ingredient> fetchIngredients(Recipe recipe){
        return recipe.getIngredients();
    }
}
