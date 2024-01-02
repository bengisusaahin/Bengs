package com.bengisu.bengs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ImageViewHolder> {
    private Context pContext;
    private List<Products> products;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    public ProductsAdapter(Context pContext, List<Products> products) {
        this.pContext = pContext;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ProductsAdapter.ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(pContext).inflate(R.layout.products_recycle_row,parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductsAdapter.ImageViewHolder holder, int position) {
        Products product = products.get(position);
        Picasso.get().load(product.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.productImage);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count ++;
                Favorite favorite = new Favorite(product.getProductName(),product.getProductImage(),product.getProductPrice());

                if (holder.count % 2 !=0){
                    holder.likeButton.setImageResource(R.drawable.favorites_added);
                    databaseReference.child("Favorites").child(user.getUid()).child(product.getProductName()).setValue(favorite);
                }else {
                    holder.likeButton.setImageResource(R.drawable.favorites);
                    databaseReference.child("Favorites").child(user.getUid()).child(product.getProductName()).removeValue();
                }
                Intent intent = new Intent (pContext,ActivityMain.class);
                pContext.startActivity(intent);
            }
        });

        databaseReference.child("Favorites").child(user.getUid()).child(product.getProductName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.likeButton.setImageResource(R.drawable.favorites_added);
                    holder.count ++;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        holder.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference cartReference = databaseReference.child("Cart").child(user.getUid()).
                        child(product.getProductName());

                ProductInCart productInCart = new ProductInCart(product.getProductName(),
                        product.getProductImage(),product.getProductPrice(),1);
                cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            cartReference.setValue(productInCart);
                            Toast.makeText(v.getContext(), "You added "+productInCart.getProductName() + " to the cart.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (pContext,ActivityMain.class);
                            pContext.startActivity(intent);
                        }
                        else
                            Toast.makeText(v.getContext(), "You have already added the " + productInCart.getProductName()
                                    + "  to your cart." , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public ImageButton likeButton;
        public ImageButton cartButton;
        public int count;

        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.productImage = itemView.findViewById(R.id.productImage);
            this.productName = itemView.findViewById(R.id.productName);
            this.productPrice = itemView.findViewById(R.id.productPrice);
            this.likeButton = itemView.findViewById(R.id.likeButton);
            this.cartButton = itemView.findViewById(R.id.cartButton);
            count= 0;
        }
    }
}
