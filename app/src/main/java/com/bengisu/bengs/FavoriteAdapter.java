package com.bengisu.bengs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ImageViewHolder> {
    private Context context;
    private List<Favorite> favorites;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    public FavoriteAdapter(Context context, List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @NotNull
    @Override
    public FavoriteAdapter.ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favorites_recycle_row,parent,false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.ImageViewHolder holder, int position) {
        Favorite favProduct= favorites.get(position);
        holder.favProductName.setText(favProduct.getProductName());
        holder.favProductPrice.setText(favProduct.getProductPrice());
        databaseReference.child("Favorites").child(firebaseUser.getUid()).child(favProduct.getProductName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    holder.likeButton.setImageResource(R.drawable.favorites_added);
                    holder.count++;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        Picasso.get().load(favProduct.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.favProductImage);

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count++;
                Favorite favorite = new Favorite(favProduct.getProductName(),favProduct.getProductImage(),favProduct.getProductPrice());

                if(holder.count % 2 != 0) {
                    holder.likeButton.setImageResource(R.drawable.favorites_added);
                    databaseReference.child("Favorites").child(firebaseUser.getUid()).child(favProduct.getProductName()).setValue(favorite);

                }

                else {
                    holder.likeButton.setImageResource(R.drawable.favorites);
                    databaseReference.child("Favorites").child(firebaseUser.getUid()).child(favProduct.getProductName()).removeValue();

                }

            }
        });
        /*Intent intent = new Intent (context,ActivityMain.class);
        context.startActivity(intent);*/
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView favProductImage;
        public TextView favProductName;
        public TextView favProductPrice;
        public ImageButton likeButton;
        public int count;

        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.favProductImage=itemView.findViewById(R.id.favProductImage);
            this.favProductName=itemView.findViewById(R.id.favProductName);
            this.favProductPrice=itemView.findViewById(R.id.favProductPrice);
            this.likeButton=itemView.findViewById(R.id.favLikeButton);
            count=0;
        }
    }
}
