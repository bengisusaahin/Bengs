package com.bengisu.bengs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TabAdapterShops extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public TabAdapterShops(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }
    public TabAdapterShops(Context c, FragmentManager fm, int totalTabs){
        super(fm);
        context = c;
        this.totalTabs=totalTabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ShopFragment();
            case 1:
                tabCategories tabCategoriesFrag = new tabCategories();
                return tabCategoriesFrag;
            case 2:
                tabFavorites tabFavoritesFrag = new tabFavorites();
                return tabFavoritesFrag;
            case 3:
                tabCart tabCartFrag = new tabCart();
                return tabCartFrag;
            case 4:
                tabAccount tabAccountFrag = new tabAccount();
                return tabAccountFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
