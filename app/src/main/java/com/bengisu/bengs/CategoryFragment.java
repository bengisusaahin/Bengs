package com.bengisu.bengs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    RecyclerView catRecyclerView;
    DatabaseReference catDatabaseReference;
    String catCurrentCategory;
    private List<CategoryProducts> catProducts;
    private CatProductsAdapter catProductsAdapter;
    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        catCurrentCategory=getActivity().getIntent().getStringExtra("currentCategory");
        System.out.println(catCurrentCategory + " aaaaa tıklandı.");
        showCatProducts(v);
        return v ;
    }

    public void showCatProducts(View v) {
        catRecyclerView= v.findViewById(R.id.category_recyclerView);
        catRecyclerView.setNestedScrollingEnabled(false);
        catRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),2);
        catRecyclerView.setLayoutManager(layoutManager);

        catProducts=new ArrayList<>();
        catDatabaseReference = FirebaseDatabase.getInstance().getReference("Category").child(catCurrentCategory);
        catDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    //Products product = postSnapshot.getValue(Products.class);
                    //System.out.println(product.getProductName());
                    CategoryProducts products = new CategoryProducts(postSnapshot.child("image").getValue().toString());
                    String name = postSnapshot.child("name").getValue().toString();
                    String price = postSnapshot.child("price").getValue().toString();
                    products.setProductName(name);
                    products.setProductPrice(price);
                    catProducts.add(products);
                }
                catProductsAdapter= new CatProductsAdapter(getContext(),catProducts);
                catRecyclerView.setAdapter(catProductsAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}