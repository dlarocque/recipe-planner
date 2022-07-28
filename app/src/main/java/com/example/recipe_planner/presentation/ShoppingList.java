package com.example.recipe_planner.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.persistence.DataAccess;

import java.util.ArrayList;

/** A {@link Fragment} representing a shopping list (stub). */
public class ShoppingList extends Fragment {

    private AccessRecipes accessRecipes;
    private DataAccess dataAccess;

    public ShoppingList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessRecipes = new AccessRecipes();
    }

    public static ShoppingList newInstance() {
        return new ShoppingList();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        ArrayList<Recipe> recipes = accessRecipes.getScheduledRecipes();

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.ingredientShoppingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ShoppingListRecyclerViewAdapter(recipes));

        return view;
    }
}
