package com.bengisu.bengs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class tabCategories extends Fragment {
    private RecyclerView cRecyclerView;
    private CategoriesAdapter cCategoriesAdapter;
    private List<Categories> cCategories;
    private DatabaseReference cDatabaseReference;
    private FirebaseDatabase cFirebaseDatabase = FirebaseDatabase.getInstance();

    //String currentCategory;

    public tabCategories(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_categories,container,false);
        //currentCategory=getActivity().getIntent().getStringExtra("currentCategory");
        //System.out.println(currentCategory + " a tıklandı.");
        showCategories(view);
        return view;
    }
    public void showCategories(View view){
        cRecyclerView = view.findViewById(R.id.categories_RecyclerView);
        cRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),2);
        cRecyclerView.setLayoutManager(layoutManager);

        cCategories=new ArrayList<>();
        cDatabaseReference = cFirebaseDatabase.getInstance().getReference("Categories");
        cDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    Categories category = new Categories(postSnapshot.child("image").getValue().toString());
                    String name = postSnapshot.child("name").getValue().toString();
                    category.setCategoryName(name);
                    cCategories.add(category);
                }
                cCategoriesAdapter= new CategoriesAdapter(getActivity(),cCategories);
                cRecyclerView.setAdapter(cCategoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
