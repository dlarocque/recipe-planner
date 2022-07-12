package com.example.recipe_planner.presentation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_planner.R;
import com.example.recipe_planner.databinding.FragmentRecipeListItemBinding;
import com.example.recipe_planner.objects.Recipe;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe}.
 */
public class RecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private final List<Recipe> recipes;
    private final OnRecipeClickListener onRecipeClickListener;
    private final OnScheduleRecipeClickListener onScheduleRecipeClickListener;

    public RecipeRecyclerViewAdapter(
            List<Recipe> items, OnRecipeClickListener onRecipeClickListener, OnScheduleRecipeClickListener onScheduleRecipeClickListener) {
        this.recipes = items;
        this.onRecipeClickListener = onRecipeClickListener;
        this.onScheduleRecipeClickListener = onScheduleRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this fragment
        return new ViewHolder(
                FragmentRecipeListItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false),
                onRecipeClickListener,
                onScheduleRecipeClickListener);
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

    public void showDatePickerDialog(View v) {
        // DialogFragment newFragment = new DatePickerFragment();
        // newFragment.show(getSupportFragmentManager(), "datePicker");
        Log.d(TAG, "pickerdialogview");
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position, View view);
    }

    public interface OnScheduleRecipeClickListener {
        void onScheduleRecipeClick(int position, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView idView;
        private final OnRecipeClickListener onRecipeClickListener;
        private final OnScheduleRecipeClickListener onScheduleRecipeClickListener;
        public ImageButton scheduleButton;

        public ViewHolder(
                FragmentRecipeListItemBinding binding,
                OnRecipeClickListener onRecipeClickListener,
                OnScheduleRecipeClickListener onScheduleRecipeClickListener) {
            super(binding.getRoot());
            // When a recipe in the list is clicked, we call the declared click listener
            idView = binding.itemNumber;
            scheduleButton = binding.getRoot().findViewById(R.id.scheduleButton);
            this.onRecipeClickListener = onRecipeClickListener;
            this.onScheduleRecipeClickListener = onScheduleRecipeClickListener;

            scheduleButton.setOnClickListener(this);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == scheduleButton) {
                onScheduleRecipeClickListener.onScheduleRecipeClick(getBindingAdapterPosition(), view);
            } else {
                onRecipeClickListener.onRecipeClick(getBindingAdapterPosition(), view);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
