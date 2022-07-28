package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.databinding.FragmentIngredientListItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** {@link RecyclerView.Adapter} that can display a {@link Ingredient}. */
public class IngredientRecyclerViewAdapter
        extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Ingredient> ingredients;

    public IngredientRecyclerViewAdapter(Recipe recipe) {
        this.ingredients = recipe.getIngredients();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this fragment
        return new ViewHolder(
                FragmentIngredientListItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for an Ingredient in the list
        Ingredient ingredientToDisplay = ingredients.get(position);
        Double ingredientAmount = ingredientToDisplay.getAmount();
        String unitName = getUnitString(ingredientToDisplay);
        holder.name.setText(ingredientToDisplay.getName());
        holder.amount.setText(String.format(Locale.getDefault(), "%.2f", ingredientAmount));
        holder.unit.setText(unitName);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public String getUnitString(Ingredient ingredient) {
        String result;
        String[] parts = ingredient.getUnit().toString().split(" ");
        if (parts.length > 1) {
            result = parts[1];
        } else {
            result = "Units";
        }
        return result;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView amount;
        public final TextView unit;

        public ViewHolder(FragmentIngredientListItemBinding binding) {
            super(binding.getRoot());

            name = binding.ingredientName;
            amount = binding.ingredientAmount;
            unit = binding.ingredientUnit;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
