package com.bengisu.bengs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView textViewUser;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth=FirebaseAuth.getInstance();

        Intent intentForName=getIntent();
        userName= intentForName.getStringExtra("userName");
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewUser= (TextView)findViewById(R.id.settings_textViewUser);
        //textViewUser.setText("Welcome, "+mAuth.getCurrentUser().getEmail()+","+userName); //su an icin null donuyo firebase detaillerinden sonra eklicem
        textViewUser.setText("Welcome, "+mAuth.getCurrentUser().getEmail()+",");
    }

    public void logout(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}