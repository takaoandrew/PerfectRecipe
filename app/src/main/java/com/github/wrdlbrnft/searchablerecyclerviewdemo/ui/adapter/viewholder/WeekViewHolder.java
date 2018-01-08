package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ItemWeekBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.WeekAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WeekModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class WeekViewHolder extends SortedListAdapter.ViewHolder<WeekModel> {

    private final ItemWeekBinding mBinding;

    public WeekViewHolder(ItemWeekBinding binding, WeekAdapter.Listener listener) {
        super(binding.getRoot());
        binding.setListener(listener);

        mBinding = binding;
    }

    @Override
    protected void performBind(@NonNull WeekModel item) {
        mBinding.setModel(item);
    }
}
