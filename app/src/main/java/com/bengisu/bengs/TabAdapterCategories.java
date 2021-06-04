package com.bengisu.bengs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TabAdapterCategories extends FragmentPagerAdapter {
    Context catContext;
    int catTotalTabs;

    public TabAdapterCategories(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    public TabAdapterCategories(@NonNull @NotNull FragmentManager fm, Context catContext, int catTotalTabs) {
        super(fm);
        this.catContext = catContext;
        this.catTotalTabs = catTotalTabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tabHomepage tabHomepageFrag = new tabHomepage();
                return tabHomepageFrag;
            case 1:
                return new CategoryFragment();
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
        return catTotalTabs;
    }
}
