package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class tabHomepage extends Fragment {
    //ViewPager viewPager;
    //CircleIndicator circleIndicator;
    private RecyclerView sRecyclerView;
    private ShopsAdapter shopsAdapter;
    private List<Shops> sShops;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    FirebaseFirestore dbfs;
    private SliderView sliderView;
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
        TextView textView = view.findViewById(R.id.homepage_textview);
        textView.setText("Discover the New Announcements!");
        //showAnnouncements(view);
        sliderDataArrayList = new ArrayList<>();
        sliderView = view.findViewById(R.id.slider);
        dbfs = FirebaseFirestore.getInstance();
        loadImages();
        showShops(view);
        return view;
    }
    /*public void showAnnouncements(View view){
        viewPager= view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleIndicator);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.announcement1);
        imageList.add(R.drawable.announcement2);
        imageList.add(R.drawable.announcement3);

        AnnouncementsAdapter myAdapter = new AnnouncementsAdapter(imageList);
        viewPager.setAdapter(myAdapter);
        circleIndicator.setViewPager(viewPager);
    }*/
    private void loadImages() {
        dbfs.collection("Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    SliderData sliderData = documentSnapshot.toObject(SliderData.class);
                    SliderData model = new SliderData();

                    model.setImgUrl(sliderData.getImgUrl());

                    sliderDataArrayList.add(model);

                    adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

                    sliderView.setSliderAdapter(adapter);
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setScrollTimeInSec(3);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to load slider data..", Toast.LENGTH_SHORT).show();
            }
        });
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
