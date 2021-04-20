package com.bengisu.bengs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public MyAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public MyAdapter(Context c, FragmentManager fm, int totalTabs){
        super(fm);
        context = c;
        this.totalTabs=totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                homepageTab homepageTabFrag = new homepageTab();
                return homepageTabFrag;
            case 1:
                categoriesTab categoriesTabFrag = new categoriesTab();
                return categoriesTabFrag;
            case 2:
                favoritesTab favoritesTabFrag = new favoritesTab();
                return favoritesTabFrag;
            case 3:
                cartTab cartTabFrag = new cartTab();
                return cartTabFrag;
            case 4:
                accountTab accountTabFrag = new accountTab();
                return accountTabFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
