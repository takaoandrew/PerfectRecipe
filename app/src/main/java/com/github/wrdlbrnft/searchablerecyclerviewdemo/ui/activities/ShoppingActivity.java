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

    private void getShoppingList(DataSnapshot dataSnapshot) {
        DataSnapshot weekSnapshot = dataSnapshot.child(mWeekTitle);
        DataSnapshot groceryListSnapshot = weekSnapshot.child("weekIngredients");
        ArrayList<String> groceriesInSection;
        TextView activeSectionList = findViewById(R.id.bakery_list);
        TextView activeSectionLabel = findViewById(R.id.bakery_title);
        for (DataSnapshot grocerySection:groceryListSnapshot.getChildren()) {
            switch (grocerySection.getKey()) {
                case "bakery":
                    activeSectionList = findViewById(R.id.bakery_list);
                    activeSectionLabel.setText("Bakery");
                    activeSectionLabel = findViewById(R.id.bakery_title);
                    break;
                case "produce":
                    activeSectionList = findViewById(R.id.produce_list);
                    activeSectionLabel.setText("Produce");
                    activeSectionLabel = findViewById(R.id.produce_title);
                    break;
                case "deli":
                    activeSectionList = findViewById(R.id.deli_list);
                    activeSectionLabel.setText("Deli");
                    activeSectionLabel = findViewById(R.id.deli_title);
                    break;
                case "dryGoods":
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel.setText("Dry Goods");
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    break;
                case "cannedGoods":
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel.setText("Canned Goods");
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    break;
                case "dairy":
                    activeSectionList = findViewById(R.id.drygoods_list);
                    activeSectionLabel.setText("Dairy");
                    activeSectionLabel = findViewById(R.id.drygoods_title);
                    break;
                case "meat":
                    activeSectionList = findViewById(R.id.meat_list);
                    activeSectionLabel.setText("Meat");
                    activeSectionLabel = findViewById(R.id.meat_title);
                    break;
                default:
                    break;
            }
            groceriesInSection = (ArrayList) grocerySection.getValue();
            activeSectionLabel.setText(grocerySection.getKey());

            for (String grocery : groceriesInSection) {
                activeSectionList.append(grocery+"\n");
            }
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
            Log.d(TAG, grocerySection.getKey());
        }


    }
}
