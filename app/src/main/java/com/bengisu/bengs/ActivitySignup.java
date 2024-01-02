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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivitySignup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName=(EditText) findViewById(R.id.signup_editTextName);

        mAuth= FirebaseAuth.getInstance();
    }

    public void createUser(View view){
        String name= editTextName.getText().toString();

        EditText editTextSurname =(EditText) findViewById(R.id.signup_editTextSurname);
        String surname = editTextSurname.getText().toString();

        EditText editTextEmail =(EditText) findViewById(R.id.signup_editTextEmail);
        String email = editTextEmail.getText().toString();

        EditText editTextPassword =(EditText) findViewById(R.id.signup_editTextPassword);
        String password = editTextPassword.getText().toString();

        EditText editTextConfirmPassword =(EditText) findViewById(R.id.signup_editTextPasswordAgain);
        String confirmPassword = editTextConfirmPassword.getText().toString();

        Users userInfo = new Users(name,surname);

        if (!password.equals(confirmPassword)) {
            Toast.makeText(ActivitySignup.this, "Password and Confirm password must be the same.", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user  =mAuth.getCurrentUser();

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(user.getUid()).setValue(userInfo);

                        System.out.println(user.getEmail());
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ActivitySignup.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void loginPage(View view){
        Intent intentLogin = new Intent(this, ActivityLogin.class);
        //intentLogin.putExtra("userName",name);
        startActivity(intentLogin);
    }
}