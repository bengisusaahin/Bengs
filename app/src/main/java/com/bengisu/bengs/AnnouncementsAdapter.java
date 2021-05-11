package com.bengisu.bengs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.database.annotations.NotNull;

import java.util.List;


public class AnnouncementsAdapter extends PagerAdapter {
    List<Integer> list;
    AnnouncementsAdapter(List<Integer> imageList){
        list=imageList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.image_layout,container,false);
        ImageView image = view.findViewById(R.id.imageView);
        image.setImageResource(list.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @org.jetbrains.annotations.NotNull ViewGroup container, int position, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        container.removeView((View) object);
    }
}
