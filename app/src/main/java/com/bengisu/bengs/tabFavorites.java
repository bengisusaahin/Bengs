package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class tabFavorites extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    RecyclerView favorite_recyclerView;
    List<Favorite> favorites;
    FavoriteAdapter favoriteAdapter;
    FirebaseUser user;

    public tabFavorites(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();

        showFavorites(v);
        return v;
    }

    public void showFavorites(View v) {
        favorite_recyclerView =v.findViewById(R.id.favorite_recyclerView);
        favorite_recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        favorite_recyclerView.setLayoutManager(layoutManager);

        favorites = new ArrayList<>();
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Favorites").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Favorite favProduct = postSnapshot.getValue(Favorite.class);
                    favorites.add(favProduct);
                }
                favoriteAdapter = new FavoriteAdapter(getContext(),favorites);
                favorite_recyclerView.setAdapter(favoriteAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}
