package com.example.windows10.riderapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.windows10.riderapp.Model.Rider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegistrationRider extends AppCompatActivity {

    private Button btnSubmit;
    private EditText riderName, riderEmail, riderContact;
    private EditText DOB, guardianName, guardianWho, guardianContact;
    private EditText password, confirmPassword;
    private RadioButton radioGenderButton, radioButtonMale, radioButtonFemale;
    private RadioGroup radioGroupGender;
    Spinner spinCity;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registration);

        btnSubmit = (Button) findViewById(R.id.riderSubmitBtn);
        riderName = (EditText) findViewById(R.id.riderName);
        riderContact = (EditText) findViewById(R.id.riderContact);
        riderEmail = (EditText) findViewById(R.id.riderEmailAddress);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);

        spinCity = (Spinner) findViewById(R.id.spinnerCity);
        String spinItem = spinCity.getSelectedItem().toString();

        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirm);

        guardianName = (EditText) findViewById(R.id.riderGuardianName);
        guardianWho = (EditText) findViewById(R.id.riderGuardianWho);
        guardianContact = (EditText) findViewById(R.id.riderGuardianContact);

        DOB = (EditText) findViewById(R.id.riderDateOfBirth);
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                DOB.setText(sdf.format(myCalendar.getTime()));




            }
        };

        DOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistrationRider.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedId);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nowYear = Calendar.getInstance().get(Calendar.YEAR);
                int yearDOB = myCalendar.get(Calendar.YEAR);

                int yearBal = nowYear - yearDOB;
                if(yearBal >=18){
                    validate();
                }else{
                    Toast.makeText(RegistrationRider.this, "Become a rider must 18 years old and above.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void validate(){
        String rName = riderName.getText().toString();
        String rContact = riderContact.getText().toString();
        String rEmail = riderEmail.getText().toString();
        String rDOB = DOB.getText().toString();
        String gName = guardianName.getText().toString();
        String gWho = guardianWho.getText().toString();
        String gContact = guardianContact.getText().toString();
        String pass = password.getText().toString();
        String cPass = confirmPassword.getText().toString();

        Boolean flag = true;

        if (rName.isEmpty()) {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Name Invalid.", Toast.LENGTH_SHORT).show();
        }


        if (!rContact.isEmpty()) {
            if (rContact.length() < 10 || rContact.length() > 11) {
                flag = false;
                Toast.makeText(RegistrationRider.this, "Contact too short or too long.", Toast.LENGTH_SHORT).show();
            }
        } else {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Invalid Contact.", Toast.LENGTH_SHORT).show();

        }

        if (!rEmail.isEmpty()) {

            if ((!rEmail.contains("@")) || (!rEmail.contains(".com"))) {
                flag = false;
                Toast.makeText(RegistrationRider.this, "Email Format Invalid.", Toast.LENGTH_SHORT).show();
            }

        } else {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Email Invalid.", Toast.LENGTH_SHORT).show();
        }

        if (!pass.isEmpty() && !cPass.isEmpty()) {

            if (pass.length() > 8) {

                if (!pass.equals(cPass)) {
                    flag = false;
                    Toast.makeText(RegistrationRider.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                }
            } else {
                flag = false;
                Toast.makeText(RegistrationRider.this, "Password length must more than 8.", Toast.LENGTH_SHORT).show();
            }

        } else {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Password Invalid.", Toast.LENGTH_SHORT).show();
        }

        if (rDOB.isEmpty()) {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Insert the birthday date.", Toast.LENGTH_SHORT).show();
        }

        if (gName.isEmpty() || gWho.isEmpty() || gContact.isEmpty()) {
            flag = false;
            Toast.makeText(RegistrationRider.this, "Emergency Information fields are required..", Toast.LENGTH_SHORT).show();
        }



        if (flag == true)
        {
            checkFirebase();

        }

    }

    public void checkFirebase(){


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_rider = database.getReference("DeliveryPerson");

        final ProgressDialog mDialog = new ProgressDialog(RegistrationRider.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();


        table_rider.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                //check if already user email
                if (dataSnapshot.child(riderContact.getText().toString()).exists()) {
                    mDialog.dismiss();
                    Toast.makeText(RegistrationRider.this, "Contact already registered...", Toast.LENGTH_SHORT).show();

                } else {
                    mDialog.dismiss();
                    Rider rid = new Rider(riderName.getText().toString(), radioGenderButton.getText().toString(), riderContact.getText().toString(), spinCity.getSelectedItem().toString(), riderEmail.getText().toString(), DOB.getText().toString(), password.getText().toString(), guardianName.getText().toString(), guardianWho.getText().toString(), guardianContact.getText().toString(), "offline");
                    table_rider.child(riderContact.getText().toString()).setValue(rid);
                    Toast.makeText(RegistrationRider.this, "Sign up successfully !!! ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegistrationRider.this, LoginRider.class);
                    startActivity(i);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
