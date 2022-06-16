package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.databinding.FragmentRecipeListItemBinding;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

/** {@link RecyclerView.Adapter} that can display a {@link Recipe}. */
public class RecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final List<Recipe> recipes;
    private final OnRecipeClickListener onRecipeClickListener;

    public RecipeRecyclerViewAdapter(
            List<Recipe> items, OnRecipeClickListener onRecipeClickListener) {
        this.recipes = items;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this fragment
        return new ViewHolder(
                FragmentRecipeListItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false),
                onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set up the view for a recipe in the list
        Recipe recipeToDisplay = recipes.get(position);
        holder.idView.setText(recipeToDisplay.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView idView;
        private final OnRecipeClickListener onRecipeClickListener;

        public ViewHolder(
                FragmentRecipeListItemBinding binding,
                OnRecipeClickListener onRecipeClickListener) {
            super(binding.getRoot());
            // When a recipe in the list is clicked, we call the declared click listener
            idView = binding.itemNumber;
            this.onRecipeClickListener = onRecipeClickListener;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecipeClickListener.onRecipeClick(getBindingAdapterPosition(), view);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
