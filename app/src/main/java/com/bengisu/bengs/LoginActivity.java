package com.bengisu.bengs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){ //When app is start, we see Homepage(now logout)
            Intent mainIntent = new Intent(this,MainActivity.class);
            startActivity(mainIntent);
        }
    }

    public void navigateUser(View view){
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        EditText editTextEmail =(EditText) findViewById(R.id.login_editTextEmailAddress);
        String email= editTextEmail.getText().toString();

        EditText editTextPassword=(EditText) findViewById(R.id.login_editTextPassword);
        String password= editTextPassword.getText().toString();

        Intent mainIntent = new Intent(this,MainActivity.class);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(mainIntent);
                }else{//email formatını kontrol ediyor
                    Toast.makeText(LoginActivity.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}