package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Kartikeya on 2/15/2018.
 */

/**
 * Fragment to display 'Explore' fragment
 */
public class ExploreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.example_fragment_explore, container, false);

        LinearLayout videosLayout = view.findViewById(R.id.videos_view);
        onClickView(videosLayout);


        return view;
    }


    public void onClickView(LinearLayout linearView) {
        switch (linearView.getId()) {
            case R.id.videos_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity().getApplicationContext(), "Test Videos", Toast.LENGTH_LONG).show();
                    }
                });
                break;

            case R.id.top_rated_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity().getApplicationContext(), "Test Videos", Toast.LENGTH_LONG).show();
                    }
                });
                break;

            case R.id.vegan_view:
                break;

            case R.id.low_fat_view:
                break;

            case R.id.sugar_conscious_view:
                break;

            case R.id.ingredients_based_view:
                break;

            case R.id.alcohol_free_view:
                break;

            case R.id.balanced_nutrition_view:
                break;

            case R.id.high_protein_view:
                break;

            case R.id.vegetarian_view:
                break;
        }
    }
}
