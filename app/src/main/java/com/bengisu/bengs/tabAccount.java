package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class tabAccount extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public tabAccount(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account,container,false);

        Button notifications = (Button)view.findViewById(R.id.account_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToNotifications = new Intent(getActivity(), ActivityNotifications.class);
                startActivity(intentToNotifications);
            }
        });
        mAuth=FirebaseAuth.getInstance();

        Button logout = (Button)view.findViewById(R.id.account_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intentToLogin = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intentToLogin);
                getActivity().finish();
            }
        });
        hello(view);
        return view;
    }
    public void hello(View view) {
        //super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            Users users = task.getResult().getValue(Users.class);
                            TextView textViewHello= view.findViewById(R.id.textViewHello);
                            //textViewUser.setText("Welcome, "+mAuth.getCurrentUser().getEmail());
                            textViewHello.setText("Hello, "+ users.name);
                        }
                        else {
                            System.out.println(task.getException().getMessage());
                        }
                    }
                });
    }
}
