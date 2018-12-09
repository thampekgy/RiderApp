package com.example.windows10.riderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    ListView viewHistory;
    List<String> lt;
    String key, orderID;

    FirebaseDatabase database;
    DatabaseReference table_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //back button
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        viewHistory = findViewById(R.id.listView_viewHistory);

        receiveOrder();





    }

    public void receiveOrder() {

        key = getIntent().getStringExtra("Phone");

        database = FirebaseDatabase.getInstance();
        table_request = database.getReference("Requests");

        table_request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lt = new ArrayList<String>();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.v("your order is " , ""+snapshot.child("deliveryPerson").getValue().toString());

                        if (snapshot.child("foodStatus").getValue().equals("Delivered")){

                            if (snapshot.child("deliveryPerson").getValue().equals(key)){

                                orderID = snapshot.getKey();
                                Log.v("your order is " , ""+orderID);
                                lt.add(orderID);
                            }

                        }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(History.this, R.layout.history_layout, R.id.txtOrderId, lt);


                    viewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(History.this, History2.class);
                            intent.putExtra("OrderID", viewHistory.getItemAtPosition(position).toString());
                            intent.putExtra("Phone", key);
                            startActivity(intent);

                        }
                    });
                    viewHistory.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
