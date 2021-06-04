package com.bengisu.bengs;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.bengisu.bengs.databinding.ActivityCatProductsBinding;
import com.google.android.material.tabs.TabLayout;

public class CatProductsActivity extends AppCompatActivity {
    TabLayout catProductsTabLayout;
    ViewPager catProductsViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_products);

        catProductsTabLayout = findViewById(R.id.catProducts_tabLayout);
        catProductsViewPager = findViewById(R.id.catProducts_viewPager);

        catProductsTabLayout.addTab(catProductsTabLayout.newTab().setIcon(R.drawable.homepage));
        catProductsTabLayout.addTab(catProductsTabLayout.newTab().setIcon(R.drawable.categories));
        catProductsTabLayout.addTab(catProductsTabLayout.newTab().setIcon(R.drawable.tabfavorites));
        catProductsTabLayout.addTab(catProductsTabLayout.newTab().setIcon(R.drawable.cart));
        catProductsTabLayout.addTab(catProductsTabLayout.newTab().setIcon(R.drawable.account));

        final TabAdapterCategories adapter= new TabAdapterCategories(getSupportFragmentManager(),this,catProductsTabLayout.getTabCount());
        catProductsViewPager.setAdapter(adapter);
        catProductsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(catProductsTabLayout));

        catProductsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                catProductsViewPager.setCurrentItem(tab.getPosition());
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