package com.bengisu.bengs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapterShops extends BaseAdapter {
    private Context mContext;

    public ImageAdapterShops(Context c) {
        mContext = c;
    }

    public int getCount() {
        return shops.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(1100, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 100, 0, 100);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(shops[position]);
        return imageView;
    }

    public Integer[] shops = {
            R.drawable.mavi, R.drawable.stradivarius, R.drawable.bershka, R.drawable.pullandbear
    };
}

