package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ItemWeekBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.WeekViewHolder;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WeekModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class WeekAdapter extends SortedListAdapter<WeekModel> {

    public interface Listener {
        void onExampleModelClicked(WeekModel model);
    }

    private final Listener mListener;

    public WeekAdapter(Context context, Comparator<WeekModel> comparator, Listener listener) {
        super(context, WeekModel.class, comparator);
        mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends WeekModel> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ItemWeekBinding binding = ItemWeekBinding.inflate(inflater, parent, false);
        return new WeekViewHolder(binding, mListener);
    }
}
