package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class WordModel implements SortedListAdapter.ViewModel {

    private final String mTaskName;
    private final int mRank;
    private final ArrayList<String> mTags;
    private final String mWord;

//    public WordModel(long id, int rank, ArrayList<String> tags, String word) {
    public WordModel(String taskName, ArrayList<String> tags, int rank, String word) {
        mTaskName = taskName;
        mRank = rank;
        mWord = word;
        mTags = tags;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public int getRank() {
        return mRank;
    }

    public String getWord() {
        return mWord;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

//    public ArrayList<String> getTags() {
////        return mTags;
//    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof WordModel) {
            final WordModel wordModel = (WordModel) item;
            return wordModel.mTaskName == mTaskName;
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof WordModel) {
            final WordModel other = (WordModel) item;
            if (mRank != other.mRank) {
                return false;
            }
            return mWord != null ? mWord.equals(other.mWord) : other.mWord == null;
        }
        return false;
    }
}
