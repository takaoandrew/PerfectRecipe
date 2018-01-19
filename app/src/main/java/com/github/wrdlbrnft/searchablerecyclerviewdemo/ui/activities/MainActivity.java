package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ActivityMainBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.WeekAdapter;
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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SortedListAdapter.Callback {

    //Helps compare for sort
    private static final Comparator<WeekModel> COMPARATOR = new SortedListAdapter.ComparatorBuilder<WeekModel>()
            .setOrderForModel(WeekModel.class, new Comparator<WeekModel>() {
                @Override
                public int compare(WeekModel a, WeekModel b) {
                    return Integer.signum(a.getRank() - b.getRank());
                }
            })
            .build();

    private WeekAdapter mAdapter;
    private ActivityMainBinding mBinding;
    private Animator mAnimator;
    private List<WeekModel> mModels;
    private DatabaseReference mDatabaseReference;
    private static final String TAG = MainActivity.class.getSimpleName();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllWeeks(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled");
            }
        });

        //Set the binding, and set the layout
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolBar);

        mAdapter = new WeekAdapter(this, COMPARATOR, new WeekAdapter.Listener() {
            @Override
            public void onExampleModelClicked(WeekModel model) {
                Context context = MainActivity.this;
                Class destinationClass = WeekDetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
                intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, model.getWeekTitle());
//                intentToStartDetailActivity.putExtra(INGREDIENTS, model.getIngredients());
                startActivity(intentToStartDetailActivity);
            }
        });

        mAdapter.addCallback(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mModels = new ArrayList<>();

    }

    private void getAllWeeks(DataSnapshot dataSnapshot){
        //waiting for all data in a single holder
        String weekCost;
        String weekTitle;
        ArrayList<String> ingredients;
        ArrayList<String> tags;
        mModels.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            weekCost = (String) singleSnapshot.child("weekCost").getValue();
            weekTitle = (String) singleSnapshot.child("weekTitle").getValue();
            Iterable recipeInformation = singleSnapshot.getChildren();
            tags = (ArrayList<String>) singleSnapshot.child("tags").getValue();
//            ingredients = (ArrayList<String>) singleSnapshot.child("weekIngredients").getValue();
            mModels.add(new WeekModel(weekTitle, tags,
//                    ingredients,
                    count+1,
                    weekCost, recipeInformation));
            count += 1;
        }
        Log.d(TAG, "mModels is: " + Arrays.toString(mModels.toArray()));
        mAdapter.edit()
                .replaceAll(mModels)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        final List<WeekModel> filteredModelList = filter(mModels, query);
        mAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<WeekModel> filter(List<WeekModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<WeekModel> filteredModelList = new ArrayList<>();
        for (WeekModel model : models) {
            final ArrayList<String> tags = model.getTags();
            StringBuilder tagStringBuilder = new StringBuilder();

            for (String string : tags) {
                tagStringBuilder.append(string);
                tagStringBuilder.append("\t");
            }
//            String tagString = String.join(",", tags);
            String tagString = tagStringBuilder.toString();

            final String text = model.getWeekCost().toLowerCase();
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

        mBinding.recyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        mBinding.recyclerView.scrollToPosition(0);
        mBinding.recyclerView.animate().alpha(1.0f);

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
