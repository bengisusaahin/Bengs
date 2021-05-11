package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class tabAccount extends Fragment {
    private FirebaseAuth mAuth;
    public tabAccount(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account,container,false);
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
        return view;
    }
}
