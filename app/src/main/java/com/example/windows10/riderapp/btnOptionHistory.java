package com.example.windows10.riderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class btnOptionHistory extends AppCompatActivity {

    String riderPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_option_history);

        Intent myIntent = getIntent();
        riderPhone = myIntent.getStringExtra("Phone");

    }

    public void dailyDelivery_click(View v){
        Intent intent = new Intent(btnOptionHistory.this, DailyHistory.class);
        intent.putExtra("Phone", riderPhone);
        startActivity(intent);

    }

    public void overallDelivery_click(View v){
        Intent intent1 = new Intent(btnOptionHistory.this, History.class);
        intent1.putExtra("Phone", riderPhone);
        startActivity(intent1);

    }
}
