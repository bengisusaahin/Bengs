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

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatProductsAdapter extends RecyclerView.Adapter<CatProductsAdapter.ImageViewHolder>{
    private Context cContext;
    private List<CategoryProducts> catProducts;

    public CatProductsAdapter(Context cContext, List<CategoryProducts> catProducts) {
        this.cContext = cContext;
        this.catProducts = catProducts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cContext).inflate(R.layout.categories_products_recycle_row,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatProductsAdapter.ImageViewHolder holder, int position) {
        CategoryProducts product = catProducts.get(position);
        Picasso.get().load(product.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.productImage);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        holder.likeButton.setImageResource(R.drawable.favorites);
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

        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.productImage = itemView.findViewById(R.id.catProductImage);
            this.productName = itemView.findViewById(R.id.catProductName);
            this.productPrice = itemView.findViewById(R.id.catProductPrice);
            this.likeButton = itemView.findViewById(R.id.catLikeButton);
        }
    }
}
