package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.RecipeDetailViewHolder;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.RecipeViewHolder;

import java.util.List;

/**
 * Created by andrewtakao on 1/10/18.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailViewHolder>{
    private List<String> mIngredientsList;
    private TextView ingredientsView;

    public RecipeDetailAdapter(List<String> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    @Override
    public RecipeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ingredientsView = parent.findViewById(R.id.ingredients);
        return new RecipeDetailViewHolder(layoutInflater.inflate(R.layout.item_recipe_detail, parent, false));

    }

    @Override
    public void onBindViewHolder(RecipeDetailViewHolder holder, int position) {
        ingredientsView = holder.itemView.findViewById(R.id.ingredients);
        ingredientsView.setText(mIngredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }
}
