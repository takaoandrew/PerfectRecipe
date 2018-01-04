package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.databinding.ActivityMainBinding;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.ExampleAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WordModel;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SortedListAdapter.Callback {

    private static final Comparator<WordModel> COMPARATOR = new SortedListAdapter.ComparatorBuilder<WordModel>()
            .setOrderForModel(WordModel.class, new Comparator<WordModel>() {
                @Override
                public int compare(WordModel a, WordModel b) {
                    return Integer.signum(a.getRank() - b.getRank());
                }
            })
            .build();

    private ExampleAdapter mAdapter;
    private ActivityMainBinding mBinding;
    private Animator mAnimator;

    private List<WordModel> mModels;

//START
//__________________________________________________________________________________________________
    private DatabaseReference mDatabaseReference;
    private static final String TAG = MainActivity.class.getSimpleName();
    int count = 0;
//__________________________________________________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//START
//__________________________________________________________________________________________________
//        FirebaseApp.initializeApp(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                taskDeletion(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//__________________________________________________________________________________________________

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolBar);

        mAdapter = new ExampleAdapter(this, COMPARATOR, new ExampleAdapter.Listener() {
            @Override
            public void onExampleModelClicked(WordModel model) {
                Context context = MainActivity.this;
                Class destinationClass = DetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
                intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, model.getTaskName());
                startActivity(intentToStartDetailActivity);
            }
        });

        mAdapter.addCallback(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mModels = new ArrayList<>();

    }
//__________________________________________________________________________________________________

    private void getAllTask(DataSnapshot dataSnapshot){
        //waiting for all data in a single holder
        String assignedTo;
        String taskName;
        ArrayList<String> tags;
//        mModels.clear();
//        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
        assignedTo = (String) dataSnapshot.child("assignedTo").getValue();
        taskName = (String) dataSnapshot.child("taskName").getValue();
        tags = (ArrayList<String>) dataSnapshot.child("tags").getValue();
//            Log.d(TAG, "the data tags are: " + dataSnapshot.child("tags").getValue());
//            Log.d(TAG, "the type is " + dataSnapshot.child("tags").getValue().getClass());
        Log.d(TAG, "the data snapshot is: " + dataSnapshot.toString());
        mModels.add(new WordModel(taskName, tags,count+1, assignedTo));
        count += 1;
//        }
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
        final List<WordModel> filteredModelList = filter(mModels, query);
        mAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<WordModel> filter(List<WordModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<WordModel> filteredModelList = new ArrayList<>();
        for (WordModel model : models) {
            final ArrayList<String> tags = model.getTags();
            String tagString = String.join(",", tags);
            final String text = model.getWord().toLowerCase();
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
