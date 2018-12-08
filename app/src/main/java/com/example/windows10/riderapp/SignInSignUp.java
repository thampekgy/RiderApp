package com.example.windows10.riderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignInSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_sign_up);
    }
    public void signIn_click(View v){
        Intent intent = new Intent(SignInSignUp.this, LoginRider.class);
        startActivity(intent);

    }


    public void signUp_click(View v){
        Intent intent = new Intent(SignInSignUp.this, RegistrationRider.class);
        startActivity(intent);

    }
}
