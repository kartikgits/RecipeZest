package com.northwindlabs.kartikeya.recipezest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

        RelativeLayout topRatedLayout = view.findViewById(R.id.top_rated_view);
        onClickView(topRatedLayout);

        RelativeLayout veganLayout = view.findViewById(R.id.vegan_view);
        onClickView(veganLayout);

        RelativeLayout lowFatLayout = view.findViewById(R.id.low_fat_view);
        onClickView(lowFatLayout);

        RelativeLayout sugarConsciousLayout = view.findViewById(R.id.sugar_conscious_view);
        onClickView(sugarConsciousLayout);

        RelativeLayout balancedLayout = view.findViewById(R.id.balanced_nutrition_view);
        onClickView(balancedLayout);

        RelativeLayout alcoholFreeLayout = view.findViewById(R.id.alcohol_free_view);
        onClickView(alcoholFreeLayout);

        RelativeLayout highProteinLayout = view.findViewById(R.id.high_protein_view);
        onClickView(highProteinLayout);

        RelativeLayout vegetarianLayout = view.findViewById(R.id.vegetarian_view);
        onClickView(vegetarianLayout);

        return view;
    }


    public void onClickView(RelativeLayout linearView) {
        switch (linearView.getId()) {

            case R.id.top_rated_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), TopRatedRecipesActivity.class);
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.vegan_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "vegan");
                        intent.putExtra("DHLabel", "health");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.low_fat_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "low-fat");
                        intent.putExtra("DHLabel", "diet");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.sugar_conscious_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "sugar-conscious");
                        intent.putExtra("DHLabel", "health");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.alcohol_free_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "alcohol-free");
                        intent.putExtra("DHLabel", "health");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.balanced_nutrition_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "balanced");
                        intent.putExtra("DHLabel", "diet");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.high_protein_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "high-protein");
                        intent.putExtra("DHLabel", "diet");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;

            case R.id.vegetarian_view:
                linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EdamamRecipeActivity.class);
                        intent.putExtra("recipeCategory", "vegetarian");
                        intent.putExtra("DHLabel", "health");
                        startActivity(intent);
                        onPause();
                    }
                });
                break;
        }
    }
}