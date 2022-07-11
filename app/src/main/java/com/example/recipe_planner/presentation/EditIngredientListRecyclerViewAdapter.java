package com.example.recipe_planner.presentation;

import android.app.AlertDialog;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.databinding.FragmentEditIngredientItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

/** {@link RecyclerView.Adapter} that can display a {@link Recipe}. */
public class EditIngredientListRecyclerViewAdapter
        extends RecyclerView.Adapter<EditIngredientListRecyclerViewAdapter.ViewHolder> {

    private final List<Ingredient> ingredients;

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
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for a recipe in the list
        Ingredient ingredientToDisplay = ingredients.get(position);
        String unitName = getUnitString(ingredientToDisplay);
        holder.name.setText(ingredientToDisplay.getName());
        holder.amount.setText(String.valueOf(ingredientToDisplay.getAmount()));
        holder.unit.setText(unitName);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView amount;
        public final TextView unit;
        public final ImageButton delete;

        public ViewHolder(FragmentEditIngredientItemBinding binding) {
            super(binding.getRoot());

            name = binding.ingredientName;
            amount = binding.ingredientAmount;
            unit = binding.ingredientUnit;
            delete = binding.deleteIngredient;

            delete.setOnClickListener(editView -> {
                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(editView.getContext());
                alertDialogBuilder.setTitle("Delete Ingredient");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_delete_foreground);
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
}
