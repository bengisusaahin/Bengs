package com.bengisu.bengs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ImageViewHolder> {

    private Context cContext;
    private List<Categories> cCategories;

    public CategoriesAdapter(Context cContext, List<Categories> cCategories) {
        this.cContext = cContext;
        this.cCategories = cCategories;
    }

    @NonNull
    @NotNull
    @Override
    public CategoriesAdapter.ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cContext).inflate(R.layout.categories_recycle_row,parent,false);
        return new CategoriesAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoriesAdapter.ImageViewHolder holder, int position) {
        Categories category = cCategories.get(position);
        Picasso.get().load(category.getCategoryImage()).placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop().into(holder.categoriesImg);
        holder.categoriesName.setText(category.getCategoryName());
        holder.categoriesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCategoryInside = new Intent(cContext,CatProductsActivity.class);
                intentToCategoryInside.putExtra("currentCategory",category.getCategoryName());
                cContext.startActivity(intentToCategoryInside);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cCategories.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoriesImg;
        public TextView categoriesName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesImg = itemView.findViewById(R.id.categoriesImage);
            categoriesName = itemView.findViewById(R.id.categoriesName);
        }
    }
}
