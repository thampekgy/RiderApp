package com.example.windows10.riderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History2 extends AppCompatActivity {

    TextView name, phone, add, status, foodName, qty;
    String name1, phone1, add1, status1, key, riderPhone, foodName1, qty1;

    ListView listView;
    List<String> lt1, lt2;

    FirebaseDatabase database;
    DatabaseReference table_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);

        //back button
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtPhone);
        add = findViewById(R.id.txtAddress);
        status = findViewById(R.id.txtStatus);
        foodName = findViewById(R.id.txtFoodName);
        qty = findViewById(R.id.txtQty);



        Intent myIntent = getIntent();
        key = myIntent.getStringExtra("OrderID");
        riderPhone = myIntent.getStringExtra("Phone");

        getRequest();



    }

    public void getRequest(){

        database = FirebaseDatabase.getInstance();
        table_request = database.getReference("Requests").child(key);

        table_request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    name1 = dataSnapshot.child("name").getValue().toString();
                    name.setText(name1);

                    phone1 = dataSnapshot.child("phone").getValue().toString();
                    phone.setText(phone1);

                    add1 = dataSnapshot.child("address").getValue().toString();
                    add.setText(add1);

                    status1 = dataSnapshot.child("foodStatus").getValue().toString();
                    status.setText(status1);

                    foodName1 = dataSnapshot.child("foods").child("cart").child("0").child("productName").getValue(String.class);
                    foodName.setText(foodName1);

                    qty1 = dataSnapshot.child("foods").child("cart").child("0").child("quantity").getValue(String.class);
                    qty.setText(qty1);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }


    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }



}
