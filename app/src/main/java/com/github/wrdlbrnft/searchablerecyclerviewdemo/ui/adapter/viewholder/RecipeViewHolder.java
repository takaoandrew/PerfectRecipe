package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ItemRecipeBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.RecipeAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.RecipeModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class RecipeViewHolder extends SortedListAdapter.ViewHolder<RecipeModel> {

    private final ItemRecipeBinding mBinding;

    public RecipeViewHolder(ItemRecipeBinding binding, RecipeAdapter.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);

        mBinding = binding;
    }

    @Override
    protected void performBind(@NonNull RecipeModel item) {
        mBinding.setModel(item);
    }
}
