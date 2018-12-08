package com.example.windows10.riderapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    EditText name, email, Gname, Gwho, Gcontact;
    TextView contact, gender, dob, city;
    String no, name1, email1, pwd, gender1, dob1, city1, Gname1, Gwho1, Gcontact1;
    Button submit;

    String key;

    FirebaseDatabase database ;
    DatabaseReference table_rider;
    MainActivity main = new MainActivity();


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Manage Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        name = (EditText) findViewById(R.id.editText_name);
        gender = (TextView) findViewById(R.id.text_gender);
        dob = (TextView) findViewById(R.id.editText_dob) ;
        contact = (TextView) findViewById(R.id.editText_contactNumber);
        email = (EditText) findViewById(R.id.editText_email);
        city = (TextView) findViewById(R.id.editText_city);

        Gname = (EditText) findViewById(R.id.editText_Gname);
        Gwho = (EditText) findViewById(R.id.editText_Gwho);
        Gcontact = (EditText) findViewById(R.id.editText_Gcontact);

        submit = (Button) findViewById(R.id.btnSubmit);

        key = getIntent().getStringExtra("Phone");

        database = FirebaseDatabase.getInstance();
        table_rider = database.getReference("DeliveryPerson").child(key);


        table_rider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {



                        name1 = dataSnapshot.child("name").getValue().toString();
                        name.setText(name1);

                        gender1 = dataSnapshot.child("gender").getValue().toString();
                        gender.setText(gender1);

                        dob1 = dataSnapshot.child("dob").getValue().toString();
                        dob.setText(dob1);

                        no = dataSnapshot.child("contact").getValue().toString();
                        contact.setText(no);

                        email1 = dataSnapshot.child("email").getValue().toString();
                        email.setText(email1);

                        city1 = dataSnapshot.child("city").getValue().toString();
                        city.setText(city1);

                        Gname1 = dataSnapshot.child("guardianName").getValue().toString();
                        Gname.setText(Gname1);

                        Gwho1 = dataSnapshot.child("guardianWho").getValue().toString();
                        Gwho.setText(Gwho1);

                        Gcontact1 = dataSnapshot.child("guardianContact").getValue().toString();
                        Gcontact.setText(Gcontact1);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //pwd = getIntent().getStringExtra("Password");

    }


    public void btnSubmitUpdate_Click(View v){

        /*Intent myIntent = getIntent();
        String key = myIntent.getStringExtra("Phone");*/


        database = FirebaseDatabase.getInstance();
        table_rider = database.getReference("DeliveryPerson").child(key);
        Log.v("your key is :",""+table_rider);

        table_rider.child("name").setValue(name.getText().toString());
        table_rider.child("email").setValue(email.getText().toString());
        table_rider.child("guardianName").setValue(Gname.getText().toString());
        table_rider.child("guardianWho").setValue(Gwho.getText().toString());
        table_rider.child("guardianContact").setValue(Gcontact.getText().toString());

        Toast.makeText(EditProfile.this, "Updated !!!", Toast.LENGTH_SHORT).show();

    }

}
