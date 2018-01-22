package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private static final String TAG = ShoppingActivity.class.getSimpleName();
    private String mWeekTitle;
    private TextView mGroceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Intent intent = getIntent();
        mWeekTitle = intent.getStringExtra(Intent.EXTRA_TEXT);
        mGroceryList = findViewById(R.id.grocery_list);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getShoppingList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled");

            }
        });


    }

    //No change- been working on sheets and script

    private void getShoppingList(DataSnapshot dataSnapshot) {
        DataSnapshot weekSnapshot = dataSnapshot.child(mWeekTitle);
        DataSnapshot groceryListSnapshot = weekSnapshot.child("weekIngredients");
        ArrayList<String> groceryArray;
        TextView activeSectionList = findViewById(R.id.bakery_list);
        TextView activeSectionLabel = findViewById(R.id.bakery_title);
        String grocerySection;
        for (DataSnapshot grocery:groceryListSnapshot.getChildren()) {
            Log.d(TAG, "ingredientName = " + grocery.getKey());
            Log.d(TAG, "sectionName.getValue() = " + grocery.child("sectionName").getValue());
            groceryArray = (ArrayList) grocery.child("sectionName").getValue();

            if (groceryArray == null) {
                return;
            }
            else {
                if (groceryArray.size() == 1)  {grocerySection = groceryArray.get(0).toLowerCase();}
                else {grocerySection = "";}
            }
            Log.d(TAG, "grocerySection = " + grocerySection);

            switch (grocerySection) {
                case "bakery":
                    Log.d(TAG, "MATCH: " + "bakery");
                    activeSectionList = findViewById(R.id.bakery_list);
                    activeSectionLabel = findViewById(R.id.bakery_title);
                    activeSectionLabel.setText("Bakery");
                    break;
                case "produce":
                    Log.d(TAG, "MATCH: " + "produce");
                    activeSectionList = findViewById(R.id.produce_list);
                    activeSectionLabel = findViewById(R.id.produce_title);
                    activeSectionLabel.setText("Produce");
                    break;
                case "deli":
                    Log.d(TAG, "MATCH: " + "deli");
                    activeSectionList = findViewById(R.id.deli_list);
                    activeSectionLabel = findViewById(R.id.deli_title);
                    activeSectionLabel.setText("Deli");
                    break;
                case "drygoods":
                    Log.d(TAG, "MATCH: " + "drygoods");
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    activeSectionLabel.setText("Dry Goods");
                    break;
                case "cannedgoods":
                    Log.d(TAG, "MATCH: " + "cannedgoods");
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    activeSectionLabel.setText("Canned Goods");
                    break;
                case "dairy":
                    Log.d(TAG, "MATCH: " + "dairy");
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    activeSectionLabel.setText("Dairy");
                    break;
                case "meat":
                    Log.d(TAG, "MATCH: " + "meat");
                    activeSectionList = findViewById(R.id.meat_list);
                    activeSectionLabel = findViewById(R.id.meat_title);
                    activeSectionLabel.setText("Meat");
                    break;
                default:
                    break;
            }
            activeSectionList.append(grocery.getKey() + "\n");
            Log.d(TAG, "Added " + grocery.getKey() + " to list: " + activeSectionList.getRootView());
//            groceriesInSection = (ArrayList) grocerySection.getValue();
//            activeSectionLabel.setText(grocery.child());


//            for (String grocery : groceriesInSection) {
//                activeSectionList.append(grocery+"\n");
//            }
//            if (grocerySection.getValue()!=null) {
//                groceriesInSection = (ArrayList) grocerySection.getValue();
//                if (groceriesInSection.get(0) != "") {
//                    mGroceryList.append(grocerySection.getKey());
//                    mGroceryList.append("\n");
//                    for (String grocery : groceriesInSection) {
//                        if (grocery.length()>=1) {
//                            Log.d(TAG, "grocery = " +grocery);
//                            mGroceryList.append(grocery+"\n");
//                        }
//                    }
//
//                }
//                Log.d(TAG, "Value is " + grocerySection.getValue());
//            }
//            Log.d(TAG, grocerySection.getKey());
        }


    }
}
