package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ActivityWeekDetailBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.IngredientAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.RecipeAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.WeekAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.RecipeModel;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WeekModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WeekDetailActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SortedListAdapter.Callback
{

    private static final Comparator<RecipeModel> COMPARATOR = new SortedListAdapter.ComparatorBuilder<RecipeModel>()
            .setOrderForModel(RecipeModel.class, new Comparator<RecipeModel>() {
                @Override
                public int compare(RecipeModel a, RecipeModel b) {
                    return Integer.signum(a.getRank() - b.getRank());
                }
            })
            .build();

    private RecipeAdapter mRecipeAdapter;
    private ActivityWeekDetailBinding mBinding;
    private Animator mAnimator;
    private List<RecipeModel> mModels;


    private RecyclerView.Adapter mIngredientAdapter;
    private ArrayList<String> mIngredientsList;

    private DatabaseReference mDatabaseReference;
    private static final String TAG = WeekDetailActivity.class.getSimpleName();
    int count = 0;

    private String mWeekTitle;
//    private ArrayList<String> mIngredients;
//    private ArrayList<String> mSteps;
    private Toolbar mToolbar;

    static final String EXTRA_RECIPE_TITLE = "recipe_title";
    static final String EXTRA_WEEK_TITLE = "week_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModels = new ArrayList<>();

        Intent intent = getIntent();
        mWeekTitle = intent.getStringExtra(Intent.EXTRA_TEXT);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllRecipes(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled");

            }
        });

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_week_detail);
        setSupportActionBar(mBinding.toolBar);

        mRecipeAdapter = new RecipeAdapter(this, COMPARATOR, new RecipeAdapter.Listener() {
            @Override
            public void onExampleModelClicked(RecipeModel model) {
                Context context = WeekDetailActivity.this;
                Class destinationClass = RecipeDetailActivity.class;
                Intent intentToStartActivity = new Intent(context, destinationClass);
                intentToStartActivity.putExtra(EXTRA_RECIPE_TITLE, model.getRecipeTitle());
                intentToStartActivity.putExtra(EXTRA_WEEK_TITLE, mWeekTitle);

                startActivity(intentToStartActivity);
            }
        });

        mRecipeAdapter.addCallback(this);
        mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recipeRecyclerView.setAdapter(mRecipeAdapter);

        mBinding.weekIngredientsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mBinding.weekIngredientsRecyclerview.setAdapter(mIngredientAdapter);
        mBinding.toolBar.setTitle(mWeekTitle);

//        setContentView(R.layout.activity_week_detail);
//        mTaskNameDisplay = (TextView) findViewById(R.id.tv_week_title);
//        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
//        mRecipeInformation = intent.getParcelableArrayListExtra(MainActivity.RECIPE_INFORMATION);
//        for (Object string: mRecipeInformation) {
//            Log.d("JKJK", "Hi " + string);
//        }
//        mIngredients = intent.getStringArrayListExtra(MainActivity.INGREDIENTS);
//        mSteps = intent.getStringArrayListExtra(MainActivity.STEPS);
//        mToolbar.setTitle(mWeekTitle);
        Log.d(TAG, "Title = " + mWeekTitle);
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


    private void getAllRecipes(DataSnapshot dataSnapshot){
        //waiting for all data in a single holder
        String recipeCost;
        String recipeTitle;
        ArrayList<String> ingredients;
        ArrayList<String> steps;
        ArrayList<String> tags;

        ArrayList<String> weekIngredients;
        mModels.clear();
        DataSnapshot weekSnapshot = dataSnapshot.child(mWeekTitle);
        DataSnapshot recipeSnapshot = weekSnapshot.child("recipes");
        for (DataSnapshot singleSnapshot : recipeSnapshot.getChildren()) {

            recipeCost = (String) singleSnapshot.child("recipeCost").getValue();
            recipeTitle = (String) singleSnapshot.child("recipeTitle").getValue();
//            Iterable recipeInformation = weekSnapshot.getChildren();
//            Log.d(TAG, "Recipe Information: " + recipeInformation);
            tags = (ArrayList<String>) singleSnapshot.child("tags").getValue();
            ingredients = (ArrayList<String>) singleSnapshot.child("ingredients").getValue();
            steps = (ArrayList<String>) singleSnapshot.child("steps").getValue();





//            Log.d(TAG, "the week title is: " + singleSnapshot.child("recipeTitle").getValue());
//            Log.d(TAG, "the recipe title is: " + singleSnapshot.child("recipeTitle").getValue());
//            Log.d(TAG, "the type is " + weekSnapshot.child("tags").getValue().getClass());
//            Log.d(TAG, "the data snapshot is: " + weekSnapshot.toString());
            mModels.add(new RecipeModel(recipeTitle, tags,
                    ingredients, steps,
                    count+1,
                    recipeCost));
            count += 1;

        }


        weekIngredients = (ArrayList<String>) weekSnapshot.child("weekIngredients").getValue();
        mIngredientsList = weekIngredients;
        mIngredientAdapter = new IngredientAdapter(mIngredientsList);

        mBinding.weekIngredientsRecyclerview.setAdapter(mIngredientAdapter);
//        Log.d(TAG, "mModels is: " + Arrays.toString(mModels.toArray()));
        mRecipeAdapter.edit()
                    .replaceAll(mModels)
                    .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<RecipeModel> filteredModelList = filter(mModels, query);
        mRecipeAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<RecipeModel> filter(List<RecipeModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<RecipeModel> filteredModelList = new ArrayList<>();
        for (RecipeModel model : models) {
            final ArrayList<String> tags = model.getTags();
            StringBuilder tagStringBuilder = new StringBuilder();

            for (String string : tags) {
                tagStringBuilder.append(string);
                tagStringBuilder.append("\t");
            }
//            String tagString = String.join(",", tags);
            String tagString = tagStringBuilder.toString();

            final String text = model.getRecipeCost().toLowerCase();
            final String rank = String.valueOf(model.getRank());
            if (tagString.contains(lowerCaseQuery) || text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onEditStarted() {
        if (mBinding.editProgressBar.getVisibility() != View.VISIBLE) {
            mBinding.editProgressBar.setVisibility(View.VISIBLE);
            mBinding.editProgressBar.setAlpha(0.0f);
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(mBinding.editProgressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        mBinding.recipeRecyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        mBinding.recipeRecyclerView.scrollToPosition(0);
        mBinding.recipeRecyclerView.animate().alpha(1.0f);

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(mBinding.editProgressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    mBinding.editProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();
    }
}

