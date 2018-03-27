package com.northwindlabs.kartikeya.recipezest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kartikeya on 2/14/2018.
 */

/**
 * The class provides appropriate {@Link Fragment} to the viewPager.
 */
public class RecipeFragmentPager extends FragmentPagerAdapter {

    // To hold the tab title
    private String[] tabText;

    private Context mContext;

    public RecipeFragmentPager(FragmentManager fragmentManager, Context context){
        super(fragmentManager);

        this.mContext = context;

        this.tabText = new String[]{
                "Local Recipes",
                "For you",
                "Explore"
        };
    }

    /**
     * Public method that will return the fragment at the
     * @param position
     */
    @Override
    public Fragment getItem(int position){
        if (position == 0){
            // Return the fragment showing Local recipes to the user.
            return new LocalRecipesFragment();
        }
        else if (position == 1){
            // Return the fragment for showing personal recipes to the user including weather based recipes.
            return new ForYouFragment();
        }
        else {
            // Return the fragment that will allow users to explore categorically all the recipes.
            return new ExploreFragment();
        }
    }

    @Override
    public int getCount(){
        // Return the total number of fragments
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabText[position];
    }
}
