package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ActivityRecipeDetailBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.RecipeDetailAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private String mRecipeTitle;
    private String mWeekTitle;
    private DatabaseReference mDatabaseReference;
    private RecyclerView.Adapter mAdapter;
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private ActivityRecipeDetailBinding mBinding;
    private ArrayList<String> mIngredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mRecipeTitle = intent.getStringExtra(WeekDetailActivity.EXTRA_RECIPE_TITLE);
        mWeekTitle = intent.getStringExtra(WeekDetailActivity.EXTRA_WEEK_TITLE);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllRecipesDetails(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled");
            }
        });


        mIngredientsList = new ArrayList<>();
        mAdapter = new RecipeDetailAdapter(mIngredientsList);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        mBinding.recipeDetailRecyclerview.setAdapter(mAdapter);
        mBinding.recipeDetailRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mBinding.toolBar.setTitle(mRecipeTitle);
    }

    private void getAllRecipesDetails(DataSnapshot dataSnapshot) {
        ArrayList<String> ingredients;
        ArrayList<String> steps;
        ArrayList<String> tags;
        DataSnapshot weeklySnapshot = dataSnapshot.child(mWeekTitle);
        DataSnapshot recipeSnapshot = weeklySnapshot.child("recipes").child(mRecipeTitle);
//        Log.d(TAG, "mRecipeTitle is :" +mRecipeTitle);
//        Log.d(TAG, "mWeekTitle is :" +mWeekTitle);
//        Log.d(TAG, "The weekly snapshot is " + weeklySnapshot);
        Log.d(TAG, "The recipe snapshot is " + recipeSnapshot);
        tags = (ArrayList<String>) recipeSnapshot.child("tags").getValue();
        ingredients = (ArrayList<String>) recipeSnapshot.child("ingredients").getValue();
        Log.d(TAG, "the ingredients are " + ingredients);
        steps = (ArrayList<String>) recipeSnapshot.child("steps").getValue();
        mIngredientsList = ingredients;
        mAdapter = new RecipeDetailAdapter(mIngredientsList);
        mBinding.recipeDetailRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        for (String ingredient: ingredients) {
//            Log.d(TAG, "The recipe ingredients are " + ingredient);
//        }
    }
}