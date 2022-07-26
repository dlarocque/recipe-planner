package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.databinding.FragmentEditIngredientItemBinding;
import com.example.recipe_planner.databinding.FragmentShoppingListBinding;
import com.example.recipe_planner.databinding.FragmentShoppingListItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRecyclerViewAdapter
        extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private AccessRecipes accessRecipes;
    private AccessIngredients accessIngredients;

    private ArrayList<Recipe> recipe;
    private List<Ingredient> ingredients;

    public ShoppingListRecyclerViewAdapter(ArrayList<Recipe> recipe) {
        this.recipe = recipe;
        ArrayList<Ingredient> gatherIngredients = new ArrayList<Ingredient>();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
        for (int i = 0; i < recipe.size(); i++) {
            gatherIngredients.addAll(accessRecipes.getRecipeIngredients(recipe.get(i).getId()));
        }
        ingredients = (List<Ingredient>) gatherIngredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingListRecyclerViewAdapter.ViewHolder(
                FragmentShoppingListItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(
            @NonNull ShoppingListRecyclerViewAdapter.ViewHolder holder, int position) {
        Ingredient ingredientToDisplay = ingredients.get(position);
        holder.shoppingIngredientName.setText(ingredientToDisplay.getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView shoppingIngredientName;
        public final TextView shoppingIngredientAmount;
        public final TextView shoppingIngredientUnit;

        public ViewHolder(FragmentShoppingListItemBinding binding) {
            super(binding.getRoot());

            shoppingIngredientName = binding.shoppingIngredientName;
            shoppingIngredientAmount = binding.shoppingIngredientAmount;
            shoppingIngredientUnit = binding.shoppingIngredientUnit;
        }
    }
}
