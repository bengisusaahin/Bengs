package com.bengisu.bengs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ActivityMain extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.homepage));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.categories));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tabfavorites));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cart));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.account));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabAdapter adapter=new TabAdapter(this,getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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