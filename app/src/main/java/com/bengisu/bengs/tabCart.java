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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tabCart extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    RecyclerView cartRecyclerView;
    List<ProductInCart> productInCarts;
    CartAdapter userCartAdapter;
    FirebaseUser user;
    TextView totalPrice;
    Button confirmCart;

    public tabCart(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart,container,false);

        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        databaseReference =  FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();

        totalPrice = v.findViewById(R.id.total);
        confirmCart = v.findViewById(R.id.confirmCart);

        //getUserInfo(v);

        showCart(v);
        return v;
    }

    public void showCart(View v) {
        cartRecyclerView= v.findViewById(R.id.cart_recyclerView);
        cartRecyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        productInCarts = new ArrayList<>();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    if(snapshot.exists()){
                        ProductInCart product = postSnapshot.getValue(ProductInCart.class);
                        productInCarts.add(product);
                    }

                }

                userCartAdapter = new CartAdapter(getContext(), productInCarts, v);
                cartRecyclerView.setAdapter(userCartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_LONG);

            }
        });

        /*confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCompleteShopping = new Intent(getActivity(), CompleteShopping.class);
                TextView totalAmount = v.findViewById(R.id.total);
                String s = totalAmount.getText().toString();
                String[] array = s.split("\\$");
                System.out.println(array[0]);
            }
        });*/
    }
}
