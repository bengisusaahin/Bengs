package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class tabHomepage extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    private RecyclerView sRecyclerView;
    private ShopsAdapter shopsAdapter;
    private List<Shops> sShops;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public tabHomepage(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_homepage,container,false);

        Button notifications = (Button)view.findViewById(R.id.homepage_notification);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToNotifications = new Intent(getActivity(), ActivityNotifications.class);
                startActivity(intentToNotifications);
            }
        });
        showAnnouncements(view);
        showShops(view);
        return view;
    }
    public void showAnnouncements(View view){
        viewPager= view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleIndicator);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.announcement1);
        imageList.add(R.drawable.announcement2);
        imageList.add(R.drawable.announcement3);

        AnnouncementsAdapter myAdapter = new AnnouncementsAdapter(imageList);
        viewPager.setAdapter(myAdapter);
        circleIndicator.setViewPager(viewPager);
    }
    public void showShops(View view){
        sRecyclerView = view.findViewById(R.id.recyclerView);
        sRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),1);
        sRecyclerView.setLayoutManager(layoutManager);

        sShops=new ArrayList<>();
        databaseReference = firebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    Shops shop = new Shops(postSnapshot.child("image").getValue().toString());
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
