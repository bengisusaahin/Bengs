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


public class ShopFragment extends Fragment {
    RecyclerView shopRecyclerView;
    DatabaseReference databaseReference;
    String currentShop;
    private List<Products> products;
    private ProductsAdapter productsAdapter;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        currentShop=getActivity().getIntent().getStringExtra("currentShop");
        System.out.println(currentShop + " a tıklandı.");
        showProducts(v);
        return v;
    }
    public void showProducts(View view){
        shopRecyclerView= view.findViewById(R.id.shop_recyclerView);
        shopRecyclerView.setNestedScrollingEnabled(false);
        shopRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),2);
        shopRecyclerView.setLayoutManager(layoutManager);

        products=new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child(currentShop);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    //Products product = postSnapshot.getValue(Products.class);
                    //System.out.println(product.getProductName());
                    Products product = new Products(postSnapshot.child("image").getValue().toString());
                    String name = postSnapshot.child("name").getValue().toString();
                    String price = postSnapshot.child("price").getValue().toString();
                    product.setProductName(name);
                    product.setProductPrice(price);
                    products.add(product);
                }
                productsAdapter= new ProductsAdapter(getContext(),products);
                shopRecyclerView.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}