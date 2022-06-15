package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
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

    public RecipeRecyclerViewAdapter(List<Recipe> items) {
        recipes = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                FragmentRecipeItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = recipes.get(position);
        holder.idView.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView idView;
        public Recipe item;

        public ViewHolder(FragmentRecipeItemBinding binding) {
            super(binding.getRoot());
            idView = binding.itemNumber;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}