package com.example.recipe_planner.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.recipe_planner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingList extends Fragment {

    public ShoppingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     *  @return A new instance of fragment ShoppingList.
     */
    public static ShoppingList newInstance() {
        return new ShoppingList();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }
}
