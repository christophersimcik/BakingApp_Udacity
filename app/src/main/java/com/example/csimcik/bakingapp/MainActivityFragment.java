package com.example.csimcik.bakingapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by csimcik on 6/15/2017.
 */
public class MainActivityFragment extends Fragment {
    public static RecipeAdapter mainAdapter;
    Activity mainFrag = getActivity();
    public LinearLayoutManager mainLinearLayoutManager;
    public RecyclerView mainListView;
    static public float adjHeight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if (MainActivity.SCREEN_CONDITION.equals("phone")) {
            view = (View) inflater.inflate(R.layout.main_act_frag, container, false);
        }else{
            view = (View) inflater.inflate(R.layout.tabletlayout, container, false);
        }
        mainListView = (RecyclerView) view.findViewById(R.id.recycler_view_main);
        mainLinearLayoutManager = new LinearLayoutManager(mainFrag);
        mainListView.setLayoutManager(mainLinearLayoutManager);
        mainAdapter = new RecipeAdapter(this.getActivity(), MainActivity.recipeCollect);
        mainListView.setAdapter(mainAdapter);
        mainListView.addItemDecoration(new DividerItemDecoration(mainFrag, ResourcesCompat.getDrawable(getResources(),R.drawable.divider_background,null)));
       if(MainActivity.recipeCollect.isEmpty()) {
           new RecipeGetter(getContext()).execute();
       }
        if(mainFrag != null) {
            DisplayMetrics display = this.getResources().getDisplayMetrics();
            float density = this.getResources().getDisplayMetrics().density;
            float width = display.widthPixels / density;
            float height = (display.heightPixels);
            adjHeight = height / 4;
            Log.i("hi ", String.valueOf(adjHeight));
        }

        return view;

    }
    private int getLastVisibleItemPosition() {
        return mainLinearLayoutManager.findLastVisibleItemPosition();
    }
    private void setRecyclerViewScrollListener() {
        mainListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = mainListView.getLayoutManager().getItemCount();

            }
        });
    }

}
