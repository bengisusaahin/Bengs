package com.bengisu.bengs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ShopActivity extends AppCompatActivity {
    TabLayout shopTabLayout;
    ViewPager shopViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopTabLayout = findViewById(R.id.shop_tabLayout);
        shopViewPager = findViewById(R.id.shop_viewPager);

        shopTabLayout.addTab(shopTabLayout.newTab().setIcon(R.drawable.homepage));
        shopTabLayout.addTab(shopTabLayout.newTab().setIcon(R.drawable.categories));
        shopTabLayout.addTab(shopTabLayout.newTab().setIcon(R.drawable.tabfavorites));
        shopTabLayout.addTab(shopTabLayout.newTab().setIcon(R.drawable.cart));
        shopTabLayout.addTab(shopTabLayout.newTab().setIcon(R.drawable.account));

        final TabAdapterShops adapter= new TabAdapterShops(this,getSupportFragmentManager(),shopTabLayout.getTabCount());
        shopViewPager.setAdapter(adapter);
        shopViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(shopTabLayout));

        shopTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                shopViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}