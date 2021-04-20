package com.bengisu.bengs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ImageViewHolder> {
    private Context pContext;
    private List<Shops> mShops;

    public ShopsAdapter(Context pContext, List<Shops> mShops) {
        this.pContext = pContext;
        this.mShops = mShops;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(pContext).inflate(R.layout.shops_recycle_row,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Shops shop = mShops.get(position);
        Picasso.get().load(shop.getShopImage()).into(holder.shopImg);
    }

    @Override
    public int getItemCount() {
        return mShops.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView shopImg;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            shopImg = itemView.findViewById(R.id.recyclerView_imageview);
        }
    }
}
