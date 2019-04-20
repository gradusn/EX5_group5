package com.example.user.ex4_group5;



import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Rest_Menu extends AppCompatActivity {
    private static final String TAG = "Rest_Menu";
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest__menu);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference Menu = mDatabase.child("rest-it-5031a");
        //Menu.setValue("This is a test value");



        Menu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String food = dataSnapshot.child("test").getValue(String.class);
                TextView textView = (TextView) findViewById(R.id.tests);

                textView.setText("test");
                //do what you want with the email

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                TextView textView = (TextView) findViewById(R.id.tests);

            }
        });


    }
}

