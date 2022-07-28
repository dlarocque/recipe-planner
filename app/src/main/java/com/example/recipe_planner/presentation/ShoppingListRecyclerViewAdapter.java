package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.business.AccessIngredients;
import com.example.recipe_planner.business.AccessRecipes;
import com.example.recipe_planner.databinding.FragmentShoppingListItemBinding;
import com.example.recipe_planner.objects.Ingredient;
import com.example.recipe_planner.objects.Recipe;
import com.example.recipe_planner.objects.measurements.ConvertibleUnit;
import com.example.recipe_planner.objects.measurements.Count;
import com.example.recipe_planner.objects.measurements.Unit;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRecyclerViewAdapter
        extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private final AccessRecipes accessRecipes;
    private final AccessIngredients accessIngredients;

    private final List<Ingredient> ingredients;

    public ShoppingListRecyclerViewAdapter(ArrayList<Recipe> recipes) {
        ArrayList<Ingredient> gatherIngredients = new ArrayList<>();
        accessRecipes = new AccessRecipes();
        accessIngredients = new AccessIngredients();
        for (int i = 0; i < recipes.size(); i++) {
            ArrayList<Ingredient> recipeIngredients =
                    new ArrayList<>(accessRecipes.getRecipeIngredients(recipes.get(i).getId()));
            for (int j = 0; j < recipeIngredients.size(); j++) {
                Ingredient recipeIngredient = recipeIngredients.get(j);
                boolean foundInShoppingList = false;
                for (int k = 0; k < gatherIngredients.size() && !foundInShoppingList; k++) {
                    if (recipeIngredient
                            .getName()
                            .equalsIgnoreCase(gatherIngredients.get(k).getName())) {
                        Ingredient shoppingIngredient = gatherIngredients.get(k);
                        double amountsSum =
                                shoppingIngredient.getAmount() + recipeIngredient.getAmount();
                        if (shoppingIngredient.getUnit() instanceof Count) {
                            shoppingIngredient.setAmount(new Count(amountsSum));
                        } else if (shoppingIngredient.getUnit() instanceof ConvertibleUnit) {
                            shoppingIngredient.setAmount(
                                    new ConvertibleUnit(
                                            ((ConvertibleUnit) recipeIngredient.getUnit())
                                                    .getUnit(),
                                            amountsSum));
                        }
                        foundInShoppingList = true;
                    }
                }
                if (!foundInShoppingList && recipeIngredient.getAmount() > 0.0) {
                    gatherIngredients.add(
                            new Ingredient(recipeIngredient.getName(), recipeIngredient.getUnit()));
                }
            }
        }
        ingredients = gatherIngredients;
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
        String unitToDisplay;
        if (ingredientToDisplay.getUnit() instanceof Count) {
            double amountToDisplay = ingredientToDisplay.getUnit().getAmount();
            unitToDisplay =
                    String.format("%.0f", amountToDisplay)
                            + " unit"
                            + (amountToDisplay == 1 ? "" : "s");
        } else if (((ConvertibleUnit) ingredientToDisplay.getUnit()).isWeight()) {
            double amountToDisplay =
                    ((ConvertibleUnit) ingredientToDisplay.getUnit()).convertTo(Unit.GRAM);
            unitToDisplay =
                    String.format("%.2f", amountToDisplay)
                            + " gram"
                            + (amountToDisplay == 1 ? "" : "s");
        } else {
            double amountToDisplay =
                    ((ConvertibleUnit) ingredientToDisplay.getUnit()).convertTo(Unit.ML);
            unitToDisplay = String.format("%.2f", amountToDisplay) + " ml";
        }
        holder.shoppingIngredientUnit.setText(unitToDisplay);
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
