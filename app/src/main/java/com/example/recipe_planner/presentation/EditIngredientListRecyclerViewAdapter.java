package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.databinding.FragmentEditIngredientItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

/** {@link RecyclerView.Adapter} that can display a {@link Recipe}. */
public class EditIngredientListRecyclerViewAdapter
        extends RecyclerView.Adapter<EditIngredientListRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    public String ingName;
    public String ingAmount;
    public String ingUnit;

    public EditIngredientListRecyclerViewAdapter(
            List<Ingredient> items){
        this.ingredients = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this fragment
        return new ViewHolder(
                FragmentEditIngredientItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false), new IngredientEditTextListener(),
                new AmountEditTextListener(), new UnitEditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for ingredients in a list
        Ingredient ingredientToDisplay = ingredients.get(position);
        String unitName = getUnitString(ingredientToDisplay);
        holder.name.setText(ingredientToDisplay.getName());
        holder.amount.setText(String.valueOf(ingredientToDisplay.getAmount()));
        holder.unit.setText(unitName);

        holder.nameListener.updatePosition(holder.getBindingAdapterPosition());
        holder.amountListener.updatePosition(holder.getBindingAdapterPosition());
        holder.unitListener.updatePosition(holder.getBindingAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText name;
        public EditText amount;
        public EditText unit;
        public final ImageButton delete;

        public IngredientEditTextListener nameListener;
        public AmountEditTextListener amountListener;
        public UnitEditTextListener unitListener;

        public ViewHolder(FragmentEditIngredientItemBinding binding, IngredientEditTextListener nameListener,
                          AmountEditTextListener amountListener, UnitEditTextListener unitListener) {
            super(binding.getRoot());

            name = binding.ingredientName;
            amount = binding.ingredientAmount;
            unit = binding.ingredientUnit;
            delete = binding.deleteIngredient;

            this.nameListener = nameListener;
            this.amountListener = amountListener;
            this.unitListener = unitListener;

            this.name.addTextChangedListener(nameListener);
            this.amount.addTextChangedListener(amountListener);
            this.unit.addTextChangedListener(unitListener);

            delete.setOnClickListener(editView -> {
                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(editView.getContext());
                alertDialogBuilder.setTitle("Delete Ingredient");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_delete_foreground);
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            dialog.dismiss();
                            Log.d("EditIngredientView", "Delete ingredient button clicked");
                            //f
                        });
                alertDialogBuilder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }

    }

    public String getUnitString(Ingredient ingredient){
        return ingredient.getUnit().getClass().getSimpleName();
    }

    private class IngredientEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Required override, stub function
        }

    }

    private class AmountEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Required override, stub function
        }

    }

    private class UnitEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Required override, stub function
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Required override, stub function
        }

    }

}
