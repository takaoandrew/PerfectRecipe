package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.WeekAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.RecipeModel;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WeekModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekDetailActivity extends AppCompatActivity {

    private static final String TAG = WeekDetailActivity.class.getSimpleName();
    private TextView mTaskNameDisplay;
    private String mWeekTitle;
//    private ArrayList<String> mIngredients;
//    private ArrayList<String> mSteps;
    int count = 0;
    private Toolbar mToolbar;
    private Iterable mRecipeInformation;


    private List<RecipeModel> mModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_detail);
        mTaskNameDisplay = (TextView) findViewById(R.id.tv_week_title);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        Intent intent = getIntent();
        mWeekTitle = intent.getStringExtra(Intent.EXTRA_TEXT);
//        mRecipeInformation = intent.getParcelableArrayListExtra(MainActivity.RECIPE_INFORMATION);
//        for (Object string: mRecipeInformation) {
//            Log.d("JKJK", "Hi " + string);
//        }
//        mIngredients = intent.getStringArrayListExtra(MainActivity.INGREDIENTS);
//        mSteps = intent.getStringArrayListExtra(MainActivity.STEPS);
        mToolbar.setTitle(mWeekTitle);
//        mTaskNameDisplay.append("Ingredients:\n");
//        for (String ingredient : mIngredients) {
//            mTaskNameDisplay.append(ingredient+"\n");
//        }
//        mTaskNameDisplay.append("Steps: ");
//        for (String step : mSteps) {
//            mTaskNameDisplay.append("\n"+step);
//        }


//        mAdapter = new WeekAdapter(this, COMPARATOR, new WeekAdapter.Listener() {
//            @Override
//            public void onExampleModelClicked(WeekModel model) {
//                Context context = MainActivity.this;
//                Class destinationClass = WeekDetailActivity.class;
//                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
//                intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, model.getWeekTitle());
////                List list = new ArrayList();
////                model.getRecipeInformation().forEach(list::add);
////                intentToStartDetailActivity.putParcelableArrayListExtra(RECIPE_INFORMATION, (ArrayList<? extends Parcelable>) list);
////                intentToStartDetailActivity.putExtra(INGREDIENTS, model.getIngredients());
////                intentToStartDetailActivity.putExtra(STEPS, model.getSteps());
//                startActivity(intentToStartDetailActivity);
//            }
//        });

    }


//    private void getAllWeeks(DataSnapshot dataSnapshot){
//        //waiting for all data in a single holder
//        String weekCost;
//        String weekTitle;
//        ArrayList<String> ingredients;
//        ArrayList<String> steps;
//        ArrayList<String> tags;
//        mModels.clear();
//        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//            weekCost = (String) singleSnapshot.child("weekCost").getValue();
//            weekTitle = (String) singleSnapshot.child("weekTitle").getValue();
//            Iterable recipeInformation = singleSnapshot.getChildren();
//            Log.d(TAG, "Recipe Information: " + recipeInformation);
//            tags = (ArrayList<String>) singleSnapshot.child("tags").getValue();
//            ingredients = (ArrayList<String>) singleSnapshot.child("ingredients").getValue();
//            steps = (ArrayList<String>) singleSnapshot.child("steps").getValue();
//
////            Log.d(TAG, "the data tags are: " + dataSnapshot.child("tags").getValue());
////            Log.d(TAG, "the type is " + dataSnapshot.child("tags").getValue().getClass());
//            Log.d(TAG, "the data snapshot is: " + singleSnapshot.toString());
//            mModels.add(new RecipeModel(weekTitle, tags,
//                    ingredients, steps,
//                    count+1,
//                    weekCost, recipeInformation));
//            count += 1;
//        }
//        Log.d(TAG, "mModels is: " + Arrays.toString(mModels.toArray()));
//        mAdapter.edit()
//                .replaceAll(mModels)
//                .commit();
//    }
}

