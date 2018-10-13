package com.example.csimcik.bakingapp;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Recipe> recipeCollect = new ArrayList<>();
    static String SCREEN_CONDITION;
    private FragmentTransaction mainFragmentTransaction;
    private FragmentManager mainFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        if(w > 2000 && h > 2000){
            SCREEN_CONDITION = "tablet";
        }else{
            SCREEN_CONDITION = "phone";
        }
        setContentView(R.layout.activity_main);
        mainFragmentManager = getSupportFragmentManager();
        mainFragmentTransaction = mainFragmentManager.beginTransaction();
        mainFragmentTransaction.replace(R.id.fragment, new MainActivityFragment());
        mainFragmentTransaction.commit();

    }

}
