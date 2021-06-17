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

public class CatProductsAdapter extends RecyclerView.Adapter<CatProductsAdapter.ImageViewHolder>{
    private Context cContext;
    private List<CategoryProducts> catProducts;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    public CatProductsAdapter(Context cContext, List<CategoryProducts> catProducts) {
        this.cContext = cContext;
        this.catProducts = catProducts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cContext).inflate(R.layout.categories_products_recycle_row,parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatProductsAdapter.ImageViewHolder holder, int position) {
        CategoryProducts product = catProducts.get(position);
        Picasso.get().load(product.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.productImage);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        //holder.likeButton.setImageResource(R.drawable.favorites);

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
                Intent intent = new Intent (cContext,ActivityMain.class);
                cContext.startActivity(intent);
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
                            Toast.makeText(v.getContext(), "You"+productInCart.getProductName() + " added to cart!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (cContext,ActivityMain.class);
                            cContext.startActivity(intent);
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
        return catProducts.size();
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
            this.productImage = itemView.findViewById(R.id.catProductImage);
            this.productName = itemView.findViewById(R.id.catProductName);
            this.productPrice = itemView.findViewById(R.id.catProductPrice);
            this.likeButton = itemView.findViewById(R.id.catLikeButton);
            this.cartButton = itemView.findViewById(R.id.catCartButton);
            count=0;
        }
    }
}
