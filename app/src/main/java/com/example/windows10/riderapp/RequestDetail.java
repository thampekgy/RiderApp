package com.example.windows10.riderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10.riderapp.Model.Delivered;
import com.example.windows10.riderapp.Model.Rider;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class RequestDetail extends AppCompatActivity {

        String key, name1, phone1, add1, foodName1, qty1, riderPhone;
        TextView orderID, name, phone, add, foodName, qty;
        Button btnDelivery;


    FirebaseDatabase database;
    DatabaseReference table_request;

    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        //back button
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        orderID = (TextView) findViewById(R.id.txtOrderID);
        name = (TextView) findViewById(R.id.txtview_name);
        phone = (TextView) findViewById(R.id.txtview_phone);
        add = (TextView) findViewById(R.id.txtview_address);
        foodName = (TextView) findViewById(R.id.textView16);
        qty = (TextView) findViewById(R.id.textView17);
        btnDelivery = (Button) findViewById(R.id.btnDeliver);

        Intent myIntent = getIntent();
        key = myIntent.getStringExtra("OrderID");
        riderPhone = myIntent.getStringExtra("Phone");
        orderID.setText(key);


        database = FirebaseDatabase.getInstance();
        table_request = database.getReference("Requests").child(key);

        table_request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name1 = dataSnapshot.child("name").getValue().toString();
                    name.setText(name1);

                    phone1 = dataSnapshot.child("phone").getValue().toString();
                    phone.setText(phone1);

                    add1 = dataSnapshot.child("address").getValue().toString();
                    add.setText(add1);

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


        database = FirebaseDatabase.getInstance();
        table_request = database.getReference("Requests").child(key);

        btnDelivery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(flag == false){

                    //btnDelivery.getText().equals("Pending");
                    btnDelivery.setText("Delivering");
                    table_request.child("deliveryPerson").setValue(riderPhone);
                    table_request.child("foodStatus").setValue(btnDelivery.getText().toString());
                    flag = true;

                }else
                {
                    btnDelivery.setText("Delivered");

                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat time = new SimpleDateFormat("h:mm a");
                    String date1 = date.format(new Date());
                    String time1 = time.format(new Date());
                    table_request.child("date").setValue(date1);
                    table_request.child("time").setValue(time1);

                    table_request.child("deliveryPerson").setValue(riderPhone);
                    table_request.child("foodStatus").setValue(btnDelivery.getText().toString());
                    /*Intent intent1 = new Intent(RequestDetail.this, Assign.class);
                    startActivity(intent1);*/

                }
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
