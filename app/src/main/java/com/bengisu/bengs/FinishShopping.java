package com.bengisu.bengs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FinishShopping extends AppCompatActivity {
    EditText deliveryAddress;
    EditText nameOfRecipient;
    EditText phoneNumber;
    String deliveryAddressStr;
    String nameOfRecipientStr;
    String phoneNumberStr;
    Button confirmAndFinishButton;
    TextView totalPrice;
    TextView price;
    String priceStr;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    List<ProductInCart> productInCarts;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_finish_shopping);

        this.deliveryAddress = findViewById(R.id.deliveryAddress);
        this.nameOfRecipient =findViewById(R.id.nameOfRecipient);
        this.phoneNumber = findViewById(R.id.phoneNumber);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        confirmAndFinishButton = findViewById(R.id.confirmAndFinish);
        totalPrice = findViewById(R.id.totalPrice);
        price = findViewById(R.id.price);
        priceStr = getIntent().getStringExtra("totalPrice");
        price.setText(priceStr+" TL");

        productInCarts = new ArrayList<>();

        confirmAndFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryAddressStr = deliveryAddress.getText().toString();
                nameOfRecipientStr = nameOfRecipient.getText().toString();
                phoneNumberStr = phoneNumber.getText().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference();

                final String currentDate, currentTime;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currDate = new SimpleDateFormat("MMM dd, yyyy");
                currentDate = currDate.format(calendar.getTime());
                SimpleDateFormat currTime = new SimpleDateFormat("HH:mm:ss a");
                currentTime = currTime.format(calendar.getTime());

                final DatabaseReference dateReference = databaseReference.child("Orders").child(user.getUid()).child(currentDate+currentTime);

                databaseReference.child("Cart").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            if (snapshot.exists()) {
                                ProductInCart product = postSnapshot.getValue(ProductInCart.class);
                                productInCarts.add(product);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
                HashMap<String, Object> orders = new HashMap<>();

                orders.put("userMail", user.getEmail());
                orders.put("deliveryAddress", deliveryAddressStr);
                orders.put("nameOfRecipient",nameOfRecipientStr );
                orders.put("phoneNumber",phoneNumberStr );
                orders.put("totalPrice", priceStr + " TL");


                dateReference.updateChildren(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Object> orderProducts = new HashMap<>();
                            for (int i = 0; i < productInCarts.size(); i++) {
                                orderProducts.put(productInCarts.get(i).getProductName(), productInCarts.get(i));
                            }
                            dateReference.child("Products").updateChildren(orderProducts);

                            databaseReference.child("Cart").child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getBaseContext(), "Your order received successfully.", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }

                    }
                });
                Intent intent = new Intent(getApplicationContext(),ActivityMain.class);
                startActivity(intent);
            }
        });
    }
}
