package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.databinding.FragmentEditIngredientItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe}.
 */
public class EditIngredientListRecyclerViewAdapter
        extends RecyclerView.Adapter<EditIngredientListRecyclerViewAdapter.ViewHolder> {

    private final AccessRecipes accessRecipes;
    private final AccessIngredients accessIngredients;

    private final Recipe recipe;
    private final List<Ingredient> ingredients;

    public EditIngredientListRecyclerViewAdapter(Recipe recipe) {
        this.recipe = recipe;
        this.ingredients = recipe.getIngredients();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this fragment
        return new ViewHolder(
                FragmentEditIngredientItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false),
                new QuantityEditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for ingredients in a list
        Ingredient ingredientToDisplay = ingredients.get(position);
        String unitName = getUnitString(ingredientToDisplay);
        holder.name.setText(ingredientToDisplay.getName());
        holder.quantity.setText(String.valueOf(ingredientToDisplay.getAmount()));
        holder.unit.setText(unitName);

        holder.quantity.addTextChangedListener(holder.quantityListener);

        holder.quantityListener.updatePosition(holder.getBindingAdapterPosition());

        holder.delete.setOnClickListener(
                editView -> {
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(editView.getContext());
                    alertDialogBuilder.setTitle("Delete Ingredient");
                    alertDialogBuilder.setIcon(R.drawable.ic_launcher_delete_foreground);
                    alertDialogBuilder.setPositiveButton(
                            "Yes",
                            (dialog, id) -> {
                                dialog.dismiss();
                                Log.d("EditIngredientView", "Delete ingredient button clicked");
                                deleteIngredientAction(position);
                            });
                    alertDialogBuilder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void deleteIngredientAction(int position) {
        Ingredient deleteIngredient = ingredients.get(position);
        String name = deleteIngredient.getName();
        double quantity = deleteIngredient.getAmount();
        String unit = deleteIngredient.getUnit().getClass().getSimpleName().toUpperCase();
        accessIngredients.deleteIngredient(recipe.getId(), name, quantity, unit);
    }

    public String getUnitString(Ingredient ingredient) {
        return ingredient.getUnit().getClass().getSimpleName();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton delete;
        public TextView name;
        public EditText quantity;
        public TextView unit;
        public QuantityEditTextListener quantityListener;

        public ViewHolder(
                FragmentEditIngredientItemBinding binding,
                QuantityEditTextListener quantityListener) {
            super(binding.getRoot());

            name = binding.ingredientName;
            quantity = binding.ingredientQuantity;
            unit = binding.ingredientUnit;
            delete = binding.deleteIngredient;

            this.quantityListener = quantityListener;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private class QuantityEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            double quantity = Double.parseDouble(charSequence.toString());
            Ingredient ingredientToModify = ingredients.get(position);
            String ingredientName = ingredientToModify.getName();
            accessIngredients.updateIngredientQuantity(recipe.getId(), quantity, ingredientName);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Required override, stub function
        }
    }
}
