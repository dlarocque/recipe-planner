package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.databinding.FragmentEditIngredientItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** {@link RecyclerView.Adapter} that can display a {@link Recipe}. */
public class EditIngredientListRecyclerViewAdapter
        extends RecyclerView.Adapter<EditIngredientListRecyclerViewAdapter.ViewHolder> {

    private AccessRecipes accessRecipes;
    private AccessIngredients accessIngredients;

    private Recipe recipe;
    private List<Ingredient> ingredients;

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
                new QuantityEditTextListener(),
                new UnitEditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for ingredients in a list
        EditText editText = new EditText(holder.name.getContext());
        Ingredient ingredientToDisplay = ingredients.get(position);
        Double ingredientAmount = ingredientToDisplay.getAmount();
        String unitName = getUnitString(ingredientToDisplay);
        holder.name.setText(ingredientToDisplay.getName());
        holder.quantity.setText(String.format(Locale.getDefault(), "%.2f", ingredientAmount));
        holder.unit.setText(unitName);

        holder.quantity.addTextChangedListener(holder.quantityListener);
        holder.unit.addTextChangedListener(holder.unitListener);

        holder.quantityListener.updatePosition(holder.getAbsoluteAdapterPosition());
        holder.unitListener.updatePosition(holder.getAbsoluteAdapterPosition());

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
                                deleteIngredientAction(holder.getAbsoluteAdapterPosition());
                                ingredients.remove(holder.getAbsoluteAdapterPosition());
                                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                            });
                    alertDialogBuilder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                });

        holder.name.setOnClickListener(
                editView -> {
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(editView.getContext());
                    alertDialogBuilder.setTitle("Edit ingredient");
                    alertDialogBuilder.setIcon(R.drawable.ic_launcher_edit_foreground);
                    alertDialogBuilder.setView(editText);
                    alertDialogBuilder.setPositiveButton(
                            "Finish",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogue, int whichButton) {
                                    String newIngredientVal = editText.getText().toString();
                                    editIngredientAction(
                                            newIngredientVal, holder.getAbsoluteAdapterPosition());
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(RecipeList.ARG_RECIPE_ID, recipe.getId());
                                    Navigation.findNavController(editView)
                                            .navigate(
                                                    R.id.action_ingredientEdit_to_recipeView,
                                                    bundle);
                                }
                            });
                    alertDialogBuilder.setNegativeButton(
                            "Cancel", (dialog, id) -> dialog.dismiss());
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
        accessIngredients.deleteIngredient(recipe.getId(), name);
    }

    public void editIngredientAction(String newName, int position) {
        Ingredient editIngredient = ingredients.get(position);
        String name = editIngredient.getName();
        accessIngredients.updateIngredientName(recipe.getId(), newName, name);
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
        public final ImageButton delete;
        public Button name;
        public EditText quantity;
        public EditText unit;
        public QuantityEditTextListener quantityListener;
        public UnitEditTextListener unitListener;

        public ViewHolder(
                FragmentEditIngredientItemBinding binding,
                QuantityEditTextListener quantityListener,
                UnitEditTextListener unitListener) {
            super(binding.getRoot());

            quantity = binding.ingredientQuantity;
            unit = binding.ingredientUnit;
            delete = binding.deleteIngredient;
            name = binding.ingredientName;

            this.quantityListener = quantityListener;
            this.unitListener = unitListener;
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
            double quantity;
            try {
                quantity = Math.abs(Double.parseDouble(charSequence.toString()));
            } catch (NumberFormatException e) {
                quantity = 0;
            }
            Ingredient ingredientToModify = ingredients.get(position);
            String ingredientName = ingredientToModify.getName();
            accessIngredients.updateIngredientQuantity(recipe.getId(), quantity, ingredientName);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Required override, stub function
        }
    }

    private class UnitEditTextListener implements TextWatcher {
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
            double quantity;
            try {
                quantity = Math.abs(Double.parseDouble(charSequence.toString()));
            } catch (NumberFormatException e) {
                quantity = 0;
            }
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
