package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.IngredientViewHolder;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.StepViewHolder;

import java.util.List;

/**
 * Created by andrewtakao on 1/10/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder>{
    private List<String> mStepsList;
    private TextView stepsView;

    public StepAdapter(List<String> stepsList) {
        mStepsList = stepsList;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        stepsView = parent.findViewById(R.id.ingredients);
        return new StepViewHolder(layoutInflater.inflate(R.layout.item_step, parent, false));

    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        stepsView = holder.itemView.findViewById(R.id.step);
        stepsView.setText(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }
}
