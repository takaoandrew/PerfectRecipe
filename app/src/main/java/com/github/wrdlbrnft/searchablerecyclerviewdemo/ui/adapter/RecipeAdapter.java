package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ItemRecipeBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.RecipeViewHolder;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.RecipeModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class RecipeAdapter extends Adapter<RecipeModel> {

    public interface Listener {
        void onExampleModelClicked(RecipeModel model);
    }

    private final Listener mListener;

    public RecipeAdapter(Context context, Comparator<RecipeModel> comparator, Listener listener) {
        super(context, RecipeModel.class, comparator);
        mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends RecipeModel> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ItemRecipeBinding binding = ItemRecipeBinding.inflate(inflater, parent, false);
        return new RecipeViewHolder(binding, mListener);
    }
}
