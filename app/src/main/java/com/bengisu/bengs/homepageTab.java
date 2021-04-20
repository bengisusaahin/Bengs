package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homepageTab extends Fragment {
    GridView gridView;
    //ListView listView;
    private RecyclerView sRecyclerView;
    private ShopsAdapter shopsAdapter;
    private List<Shops> sShops;
    private DatabaseReference databaseReference;

    public homepageTab(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.homepage_fragment,container,false);
        gridView= view.findViewById(R.id.homepage_gridview);
        //listView= view.findViewById(R.id.recyclerView);

        Button notifications = (Button)view.findViewById(R.id.homepage_notification);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToNotifications = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(intentToNotifications);
            }
        });

        gridView.setAdapter(new ImageAdapter(this.getContext()));
        //listView.setAdapter(new ImageAdapterShops(this.getContext()));
        showShops(view);
        return view;
    }
    public void showShops(View view){
        sRecyclerView = view.findViewById(R.id.recyclerView);
        sRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getActivity(),2);
        sRecyclerView.setLayoutManager(layoutManager);

        sShops=new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    Shops shop = postSnapshot.getValue(Shops.class);
                    sShops.add(shop);
                }
                shopsAdapter= new ShopsAdapter(getActivity(),sShops);
                sRecyclerView.setAdapter(shopsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
