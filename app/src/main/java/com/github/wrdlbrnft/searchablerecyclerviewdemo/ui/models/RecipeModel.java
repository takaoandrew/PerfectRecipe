package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class RecipeModel implements SortedListAdapter.ViewModel {

    private final String mRecipeTitle;
    private final int mRank;
    private final ArrayList<String> mTags;
    private final ArrayList<String> mIngredients;
    private final ArrayList<String> mSteps;
    private final String mRecipeCost;

//    public RecipeModel(long id, int rank, ArrayList<String> tags, String word) {
    public RecipeModel(String weekTitle,
                       ArrayList<String> tags,
                       ArrayList<String> ingredients,
                       ArrayList<String> steps,
                       int rank,
                       String weekCost) {
        mRecipeTitle = weekTitle;
        mRank = rank;
        mRecipeCost = weekCost;
        mTags = tags;
        mIngredients = ingredients;
        mSteps = steps;
    }

    public String getWeekTitle() {
        return mRecipeTitle;
    }

    public int getRank() {
        return mRank;
    }

    public String getWeekCost() {
        return mRecipeCost;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }
    public ArrayList<String> getIngredients() { return mIngredients; }
    public ArrayList<String> getSteps() { return mSteps; }

//    public ArrayList<String> getTags() {
////        return mTags;
//    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof RecipeModel) {
            final RecipeModel recipeModel = (RecipeModel) item;
            return recipeModel.mRecipeTitle == mRecipeTitle;
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof RecipeModel) {
            final RecipeModel other = (RecipeModel) item;
            if (mRank != other.mRank) {
                return false;
            }
            return mRecipeCost != null ? mRecipeCost.equals(other.mRecipeCost) : other.mRecipeCost == null;
        }
        return false;
    }
}
