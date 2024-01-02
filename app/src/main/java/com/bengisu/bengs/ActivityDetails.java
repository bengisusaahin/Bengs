package com.bengisu.bengs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityDetails extends AppCompatActivity {
    TextView personal;
    TextView name;
    TextView surname;
    TextView email;
    //TextView phone;
    Button update;

    FirebaseAuth user;
    private DatabaseReference dDatabase;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        personal=findViewById(R.id.details_personal);
        name=findViewById(R.id.details_name);
        surname=findViewById(R.id.details_surname);
        email=findViewById(R.id.details_email);
        //phone=findViewById(R.id.details_phone);

        user=FirebaseAuth.getInstance();
        dDatabase= FirebaseDatabase.getInstance().getReference();
        dDatabase.child("Users").child(user.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            Users users = task.getResult().getValue(Users.class);
                            name.setText("Name: "+ users.name);
                            surname.setText("Surname: "+users.surname);
                            email.setText("Email: "+user.getCurrentUser().getEmail());
                            //phone.setText("Phone Number: "+user.getCurrentUser().getPhoneNumber());
                        }
                        else {
                            System.out.println(task.getException().getMessage());
                        }
                    }
                });
    }

}
