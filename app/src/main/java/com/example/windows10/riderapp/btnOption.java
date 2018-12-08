package com.example.windows10.riderapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class btnOption extends Fragment {


    TextView name;
    Button btnEdit, btnAssign, confirm;
    String phone, name1, status1;

    TextView txtStatus;
    Spinner status;

    public btnOption() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_btn_option, container, false);

        name = (TextView) v.findViewById(R.id.textView3);
        btnEdit = (Button) v.findViewById(R.id.btnEditProfile);
        btnAssign = (Button)v.findViewById(R.id.btnWork);
        txtStatus = (TextView) v.findViewById(R.id.textView_onOff);
        status = (Spinner) v.findViewById(R.id.spinnerOnOff);
        status1 = status.getSelectedItem().toString();


        confirm = (Button) v.findViewById(R.id.btnConfirm);


        phone = getActivity().getIntent().getExtras().getString("Phone");
        name1 = getActivity().getIntent().getExtras().getString("name");
        name.setText(name1);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status1 = status.getSelectedItem().toString();


                if (status1.equals("Online")){
                    btnAssign.setEnabled(true);
                    btnAssign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), Assign.class);
                            intent.putExtra("Phone", phone);
                            startActivity(intent);
                        }
                    });

                }else{
                    btnAssign.setEnabled(false);
                }
            }
        });





        return v;
    }





}
