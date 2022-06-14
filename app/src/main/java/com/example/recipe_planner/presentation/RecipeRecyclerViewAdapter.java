package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.databinding.FragmentRecipeItemBinding;
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

        return new ViewHolder(
                FragmentRecipeItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false),
                onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.recipe = recipes.get(position);
        holder.idView.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView idView;
        public Recipe recipe;
        OnRecipeClickListener onRecipeClickListener;

        public ViewHolder(
                FragmentRecipeItemBinding binding, OnRecipeClickListener onRecipeClickListener) {
            super(binding.getRoot());
            idView = binding.itemNumber;
            this.onRecipeClickListener = onRecipeClickListener;

            idView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecipeClickListener.onRecipeClick(getBindingAdapterPosition(), view);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
