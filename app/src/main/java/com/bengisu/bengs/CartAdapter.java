package com.bengisu.bengs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder>{
    private Context context;
    private List<ProductInCart> productInCart;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    TextView total;
    double totalPrice = 0;
    Button confirmAndFinish;// ama bunun burda olmaması gerek ki sepetteki ürünlerin nasıl görünecğini ayarladığımız adapter değil mi bu

    public CartAdapter(Context context, List<ProductInCart> productInCart, View view) {
        this.context = context;
        this.productInCart = productInCart;
        this.total = view.findViewById(R.id.total);
        //this.confirmAndFinish = view.findViewById(R.id.completeShoppingButton);
    }

    @NonNull
    @NotNull
    @Override
    public CartAdapter.ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_recycle_row, parent, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        total.setText("       ");
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.ImageViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ProductInCart product = productInCart.get(position);
        DatabaseReference UserCartReference = databaseReference.child("Cart").child(user.getUid());

        String[] price = product.getProductPrice().split(" TL");
        String currProductPrice = price[0];

        holder.productName.setText(product.getProductName());
        Picasso.get().load(product.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.productImage);

        final int[] currAmount = new int[1];
        UserCartReference.child(product.getProductName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                if(snapshot.exists() && !check){
                    currAmount[0] = Integer.parseInt(snapshot.child("amount").getValue().toString());
                    holder.currentAmount.setText(currAmount[0] + "");
                    double totalPriceOfCurrentProduct = currAmount[0] * Double.parseDouble(currProductPrice);
                    totalPrice += totalPriceOfCurrentProduct;
                    total.setText(totalPrice + " TL");

                    holder.productPrice.setText((Double.parseDouble(currProductPrice)) * currAmount[0] + " TL");
                }
                else
                    check = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.increaseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int[] currAmount = new int[1];

                UserCartReference.child(product.getProductName()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists() ) {
                            currAmount[0] = Integer.parseInt(snapshot.child("amount").getValue().toString());
                            UserCartReference.child(product.getProductName()).child("amount").setValue(currAmount[0] + 1)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            holder.currentAmount.setText(currAmount[0] + 1 + "");
                                            product.setAmount(currAmount[0] + 1 );
                                            Toast.makeText(context, "You increse the desired amount successfully.",Toast.LENGTH_LONG).show();
                                            Intent goToMain = new Intent (context, ActivityMain.class);
                                            v.getContext().startActivity(goToMain);
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        holder.decreaseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] currAmount = new int[1];

                UserCartReference.child(product.getProductName()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            currAmount[0] = Integer.parseInt(snapshot.child("amount").getValue().toString());
                            if (currAmount[0] != 1){
                                UserCartReference.child(product.getProductName()).child("amount").setValue(currAmount[0] - 1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                holder.currentAmount.setText(currAmount[0] - 1 + "");
                                                product.setAmount(currAmount[0] - 1 );
                                                Toast.makeText(context, "You decrease the desired amount successfully.",Toast.LENGTH_LONG).show();
                                                Intent goToMain = new Intent (context, ActivityMain.class);
                                                v.getContext().startActivity(goToMain);
                                            }
                                        });
                            }
                            else {
                                UserCartReference.child(product.getProductName()).child("amount").setValue(0);
                                FirebaseDatabase.getInstance().getReference().child("Cart").child(user.getUid()).child(product.getProductName()).removeValue();
                                Intent goToMain = new Intent (context, ActivityMain.class);
                                v.getContext().startActivity(goToMain);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        /*confirmAndFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCompleteShopping = new Intent(context, CompleteShopping.class);
                String s = totalPriceText.getText().toString();
                String[] array = s.split("\\$");
                System.out.println(array[0]);
                goToCompleteShopping.putExtra("totalPrice", array[0] +"");
                v.getContext().startActivity(goToCompleteShopping);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return productInCart.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public TextView currentAmount;
        public Button increaseProduct;
        public Button decreaseProduct;

        View view;

        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.cartProductName);
            this.productImage = itemView.findViewById(R.id.cartProductImage);
            this.productPrice = itemView.findViewById(R.id.cart_productPrice);
            this.currentAmount = itemView.findViewById(R.id.currentAmount);
            this.increaseProduct = itemView.findViewById(R.id.increaseProduct);
            this.decreaseProduct = itemView.findViewById(R.id.decreaseProduct);

            view = itemView;
        }
    }
}
