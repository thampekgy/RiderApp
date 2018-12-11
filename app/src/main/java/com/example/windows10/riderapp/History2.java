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

import com.example.windows10.riderapp.Model.Delivered;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History2 extends AppCompatActivity {

    TextView name, phone, add, status, foodName, qty, date, time;
    String name1, phone1, add1, status1, date1, time1, key, riderPhone, foodName1, qty1;

    ListView listView;
    List<String> lt;

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
        date = findViewById(R.id.txtDate);
        time = findViewById(R.id.txtTime);


        Intent myIntent = getIntent();
        key = myIntent.getStringExtra("OrderID");
        riderPhone = myIntent.getStringExtra("Phone");

        getRequest();
        receiveOrder();



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

                    date1 = dataSnapshot.child("date").getValue().toString();
                    date.setText(date1);

                    time1 = dataSnapshot.child("time").getValue().toString();
                    time.setText(time1);

                    status1 = dataSnapshot.child("foodStatus").getValue().toString();
                    status.setText(status1);

                    /*foodName1 = dataSnapshot.child("foods").child("cart").child("0").child("productName").getValue(String.class);
                    foodName.setText(foodName1);*/
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    public void receiveOrder() {

        listView = (ListView) findViewById(R.id.historyFoodsList);

        database = FirebaseDatabase.getInstance();
        table_request = database.getReference("Requests").child(key);

        table_request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lt = new ArrayList<String>();
                if (dataSnapshot.exists()) {

                        if (dataSnapshot.child("foodStatus").getValue().equals("Delivered")){

                            if (dataSnapshot.child("deliveryPerson").getValue().equals(riderPhone)){

                                int count = (int) dataSnapshot.child("foods").child("cart").getChildrenCount();


                                    for (int i = 0; i < count ; i++){
                                        String name =dataSnapshot.child("foods").child("cart").child(i+"").child("productName").getValue().toString();

                                        lt.add(name);
                                    }

                            }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(History2.this, R.layout.history2_layout, R.id.txtvFoodName, lt);
                    listView.setAdapter(adapter);
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
