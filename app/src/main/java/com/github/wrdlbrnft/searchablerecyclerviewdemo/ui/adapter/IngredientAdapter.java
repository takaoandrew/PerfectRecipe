package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.IngredientViewHolder;

import java.util.List;

/**
 * Created by andrewtakao on 1/10/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder>{
    private List<String> mIngredientsList;
    private TextView ingredientsView;

    public IngredientAdapter(List<String> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ingredientsView = parent.findViewById(R.id.ingredients);
        return new IngredientViewHolder(layoutInflater.inflate(R.layout.item_ingredient, parent, false));

    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        ingredientsView = holder.itemView.findViewById(R.id.ingredient);
        ingredientsView.setText(mIngredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }
}
