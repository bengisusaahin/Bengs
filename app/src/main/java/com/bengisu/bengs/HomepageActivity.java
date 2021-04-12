package com.bengisu.bengs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        GridView gridView = findViewById(R.id.homepage_gridview);
        ListView listView = findViewById(R.id.homepage_listview);
        //ImageView imageView = findViewById(R.id.homepage_shop1);

        //Data
        /*ArrayList<String> news = new ArrayList<>();
        news.add("New Collection");
        news.add("Just for You");
        news.add("Coupon");*/

        /*ArrayList<String> shopsNames = new ArrayList<>();
        shopsNames.add("Mavi");
        shopsNames.add("Stradivarius");
        shopsNames.add("Bershka");
        shopsNames.add("Pull and Bear");

        Bitmap mavi = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.mavi);
        Bitmap stradivarius = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.stradivarius);

        ArrayList<Bitmap>shopsImages = new ArrayList<>();
        shopsImages.add(mavi);
        shopsImages.add(stradivarius);*/
        //ListView
        /*ArrayAdapter adapterNews = new ArrayAdapter(this, android.R.layout.simple_list_item_1,news);
        gridView.setAdapter(adapterNews);*/
        gridView.setAdapter(new ImageAdapter(this));
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,shopsNames);
        listView.setAdapter(new ImageAdapterShops(this));
    }
}