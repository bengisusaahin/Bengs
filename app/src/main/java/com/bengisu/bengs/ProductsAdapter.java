package com.bengisu.bengs;

import android.content.Context;
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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ImageViewHolder> {
    private Context pContext;
    private List<Products> products;

    public ProductsAdapter(Context pContext, List<Products> products) {
        this.pContext = pContext;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ProductsAdapter.ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(pContext).inflate(R.layout.products_recycle_row,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductsAdapter.ImageViewHolder holder, int position) {
        Products product = products.get(position);
        Picasso.get().load(product.getProductImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.productImage);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        holder.likeButton.setImageResource(R.drawable.favorites);
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

        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.productImage = itemView.findViewById(R.id.productImage);
            this.productName = itemView.findViewById(R.id.productName);
            this.productPrice = itemView.findViewById(R.id.productPrice);
            this.likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}
