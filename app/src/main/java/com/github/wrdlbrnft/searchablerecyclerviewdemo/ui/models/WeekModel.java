package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class WeekModel implements SortedListAdapter.ViewModel {

    private final String mWeekTitle;
    private final int mRank;
    private final ArrayList<String> mTags;
    private final String mWeekCost;

//    public WeekModel(long id, int rank, ArrayList<String> tags, String word) {
    public WeekModel(String weekTitle, ArrayList<String> tags, int rank, String weekCost) {
        mWeekTitle = weekTitle;
        mRank = rank;
        mWeekCost = weekCost;
        mTags = tags;
    }

    public String getWeekTitle() {
        return mWeekTitle;
    }

    public int getRank() {
        return mRank;
    }

    public String getWeekCost() {
        return mWeekCost;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

//    public ArrayList<String> getTags() {
////        return mTags;
//    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof WeekModel) {
            final WeekModel weekModel = (WeekModel) item;
            return weekModel.mWeekTitle == mWeekTitle;
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof WeekModel) {
            final WeekModel other = (WeekModel) item;
            if (mRank != other.mRank) {
                return false;
            }
            return mWeekCost != null ? mWeekCost.equals(other.mWeekCost) : other.mWeekCost == null;
        }
        return false;
    }
}
