package com.example.recipe_planner.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private final OnEditListener mOnEditListener;

    public RecipeRecyclerViewAdapter(List<Recipe> items, OnEditListener onEditListener) {
        recipes = items;
        this.mOnEditListener = onEditListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder VH =
                new ViewHolder(
                        FragmentRecipeItemBinding.inflate(
                                LayoutInflater.from(parent.getContext()), parent, false),
                        mOnEditListener);
        return VH;
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

    public interface OnEditListener {
        void onEditClick(String name, View view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView idView;
        public Recipe item;
        public ImageButton edit;
        OnEditListener onEditListener;

        public ViewHolder(FragmentRecipeItemBinding binding, OnEditListener onEditListener) {
            super(binding.getRoot());
            idView = binding.itemNumber;
            edit = binding.editRecipe;
            this.onEditListener = onEditListener;

            edit.setOnClickListener(this);
        }

        public void onClick(View view) {
            onEditListener.onEditClick(recipes.get(getBindingAdapterPosition()).getName(), view);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
