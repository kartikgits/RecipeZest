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

        LinearLayout topRatedLayout = view.findViewById(R.id.top_rated_view);
        onClickView(topRatedLayout);

        LinearLayout veganLayout = view.findViewById(R.id.vegan_view);
        onClickView(veganLayout);

        LinearLayout lowFatLayout = view.findViewById(R.id.low_fat_view);
        onClickView(lowFatLayout);

        LinearLayout sugarConsciousLayout = view.findViewById(R.id.sugar_conscious_view);
        onClickView(sugarConsciousLayout);

        LinearLayout balancedLayout = view.findViewById(R.id.balanced_nutrition_view);
        onClickView(balancedLayout);

        LinearLayout alcoholFreeLayout = view.findViewById(R.id.alcohol_free_view);
        onClickView(alcoholFreeLayout);

        LinearLayout highProteinLayout = view.findViewById(R.id.high_protein_view);
        onClickView(highProteinLayout);

        LinearLayout vegetarianLayout = view.findViewById(R.id.vegetarian_view);
        onClickView(vegetarianLayout);

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

            case R.id.ingredients_based_view:
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