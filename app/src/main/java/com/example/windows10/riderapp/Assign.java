package com.example.windows10.riderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Assign extends AppCompatActivity {

    String key, name1, status1, orderID;
    TextView name, txtStatus, txtListView;
    Spinner status;
    Button confirm;
    ListView listView;
    List<String> lt;


    FirebaseDatabase database;
    DatabaseReference table_rider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        getSupportActionBar().setTitle("Assign");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.textView_name);
        txtStatus = (TextView) findViewById(R.id.textView_onOff);
        confirm = (Button) findViewById(R.id.btnConfirm);
        txtListView = (TextView) findViewById(R.id.txtview_orderID);

        status = (Spinner) findViewById(R.id.spinnerOnOff);


        key = getIntent().getStringExtra("Phone");

        database = FirebaseDatabase.getInstance();
        table_rider = database.getReference("DeliveryPerson").child(key);


        table_rider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name1 = dataSnapshot.child("name").getValue().toString();
                    name.setText(name1);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        receiveOrder();
    }

    public void receiveOrder() {

        listView = (ListView) findViewById(R.id.listView_orderId);

        database = FirebaseDatabase.getInstance();
        table_rider = database.getReference("Requests");

        table_rider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lt = new ArrayList<String>();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        if (snapshot.child("foodStatus").getValue().equals("pending")){

                            orderID = snapshot.getKey();
                            Log.v("your order is " , ""+orderID);
                            lt.add(orderID);
                        }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Assign.this, R.layout.task, R.id.txtview_orderID, lt);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(Assign.this, RequestDetail.class);
                            intent.putExtra("OrderID", listView.getItemAtPosition(position).toString());
                            startActivity(intent);

                        }
                    });
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
