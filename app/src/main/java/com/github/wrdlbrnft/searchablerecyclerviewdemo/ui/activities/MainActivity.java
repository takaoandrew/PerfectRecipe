package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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

//START
//__________________________________________________________________________________________________
    private DatabaseReference mDatabaseReference;
    private static final String TAG = MainActivity.class.getSimpleName();
    int count = 0;
    static final String INGREDIENTS = "ingredients";
    static final String STEPS = "steps";
    static final String WEEK_TITLE = "week_title";
    static final String RECIPE_INFORMATION = "recipe_information";
//__________________________________________________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//START
//__________________________________________________________________________________________________
//        FirebaseApp.initializeApp(this);
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

//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getAllWeeks(dataSnapshot);
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                getAllWeeks(dataSnapshot);
//            }
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
////                taskDeletion(dataSnapshot);
//            }
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//__________________________________________________________________________________________________

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolBar);

        mAdapter = new WeekAdapter(this, COMPARATOR, new WeekAdapter.Listener() {
            @Override
            public void onExampleModelClicked(WeekModel model) {
                Context context = MainActivity.this;
                Class destinationClass = WeekDetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
                intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, model.getWeekTitle());
//                List list = new ArrayList();
//                model.getRecipeInformation().forEach(list::add);
//                intentToStartDetailActivity.putParcelableArrayListExtra(RECIPE_INFORMATION, (ArrayList<? extends Parcelable>) list);
//                intentToStartDetailActivity.putExtra(INGREDIENTS, model.getIngredients());
//                intentToStartDetailActivity.putExtra(STEPS, model.getSteps());
//                intentToStartDetailActivity.putExtra(WEEK_TITLE, model.getWeekTitle());
                startActivity(intentToStartDetailActivity);
            }
        });

        mAdapter.addCallback(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mModels = new ArrayList<>();

    }
//__________________________________________________________________________________________________

    private void printData(DataSnapshot dataSnapshot) {
        Log.d(TAG, "the data snapshot is: " + dataSnapshot.toString());
        long numChildren = dataSnapshot.getChildrenCount();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            Log.d(TAG, "the child is " + child);

        }
    }

    private void getAllWeeks(DataSnapshot dataSnapshot){
        //waiting for all data in a single holder
        String weekCost;
        String weekTitle;
        ArrayList<String> ingredients;
        ArrayList<String> steps;
        ArrayList<String> tags;
        mModels.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            weekCost = (String) singleSnapshot.child("weekCost").getValue();
            weekTitle = (String) singleSnapshot.child("weekTitle").getValue();
            Iterable recipeInformation = singleSnapshot.getChildren();
            Log.d(TAG, "Recipe Information: " + recipeInformation);
            tags = (ArrayList<String>) singleSnapshot.child("tags").getValue();
//            ingredients = (ArrayList<String>) singleSnapshot.child("ingredients").getValue();
//            steps = (ArrayList<String>) singleSnapshot.child("steps").getValue();

//            Log.d(TAG, "the data tags are: " + dataSnapshot.child("tags").getValue());
//            Log.d(TAG, "the type is " + dataSnapshot.child("tags").getValue().getClass());
            Log.d(TAG, "the data snapshot is: " + singleSnapshot.toString());
            mModels.add(new WeekModel(weekTitle, tags,
//                    ingredients, steps,
                    count+1,
                    weekCost, recipeInformation));
            count += 1;
        }
        Log.d(TAG, "mModels is: " + Arrays.toString(mModels.toArray()));
        mAdapter.edit()
                .replaceAll(mModels)
                .commit();
    }

//
//    private void taskDeletion(DataSnapshot dataSnapshot){
//        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//            String taskTitle = singleSnapshot.getValue(String.class);
//            for(int i = 0; i < allTask.size(); i++){
//                if(allTask.get(i).getTask().equals(taskTitle)){
//                    allTask.remove(i);
//                }
//            }
//            Log.d(TAG, "Task tile " + taskTitle);
//            recyclerViewAdapter.notifyDataSetChanged();
//            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, allTask);
//            recyclerView.setAdapter(recyclerViewAdapter);
//        }
//    }
//__________________________________________________________________________________________________

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
