package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;

/**
 * Created by andrewtakao on 1/7/18.
 */

public class WeekModel implements SortedListAdapter.ViewModel {

    private final String mWeekTitle;
    private final int mRank;
    private final ArrayList<String> mTags;
//    private final ArrayList<String> mIngredients;
//    private final ArrayList<String> mSteps;
    private final String mWeekCost;
    private final Iterable mRecipeInformation;

    public WeekModel(String weekTitle,
                       ArrayList<String> tags,
//                       ArrayList<String> ingredients,
//                       ArrayList<String> steps,
                       int rank,
                       String weekCost,
                       Iterable recipeInformation)
    {
        mWeekTitle = weekTitle;
        mRank = rank;
        mWeekCost = weekCost;
        mTags = tags;
        mRecipeInformation = recipeInformation;
//        mIngredients = ingredients;
//        mSteps = steps;
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

    public Iterable getRecipeInformation() {
        return mRecipeInformation;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }
//    public ArrayList<String> getIngredients() { return mIngredients; }
//    public ArrayList<String> getSteps() { return mSteps; }

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
