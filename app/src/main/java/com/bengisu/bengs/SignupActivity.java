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
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth= FirebaseAuth.getInstance();
    }

    public void createUser(View view){
        EditText editTextName =(EditText) findViewById(R.id.signup_editTextName);
        String name = editTextName.getText().toString();

        EditText editTextSurname =(EditText) findViewById(R.id.signup_editTextSurname);
        String surname = editTextSurname.getText().toString();

        EditText editTextEmail =(EditText) findViewById(R.id.signup_editTextEmail);
        String email = editTextEmail.getText().toString();

        EditText editTextPassword =(EditText) findViewById(R.id.signup_editTextPassword);
        String password = editTextPassword.getText().toString();

        EditText editTextConfirmPassword =(EditText) findViewById(R.id.signup_editTextPasswordAgain);
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignupActivity.this, "Password and Confirm password must be the same.", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user  =mAuth.getCurrentUser();
                        System.out.println(user.getEmail());
                    }else{
                        Toast.makeText(SignupActivity.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void loginPage(View view){
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}