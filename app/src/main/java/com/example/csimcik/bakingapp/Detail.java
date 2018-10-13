package com.example.csimcik.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


public class Detail extends AppCompatActivity {
    TextView header;
    static RecyclerView ingView;
    static RecyclerView insView;
    IngredientsAdapter ingAdapter;
    InstructionAdapter instructionAdapter;
    LinearLayoutManager ingLayoutMang;
    static LinearLayoutManager insLayoutMang;
    Context detailContext;
    static int recipeslct;
    static FragmentTransaction detFragmentTransaction;
    static FragmentManager detFragmentManager;
    static TextView vidNumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(MainActivity.SCREEN_CONDITION.equals("phone")) {
                setContentView(R.layout.activity_detail);
            }else{
                setContentView(R.layout.activity_detail_vert_tablet);
            }
        }else{
            if(MainActivity.SCREEN_CONDITION.equals("phone")) {
                setContentView(R.layout.activity_detail_landscape);
            }else{
                setContentView(R.layout.activity_detail_land_tablet);
            }
        }
        detailContext = Detail.this ;
        header = (TextView)findViewById(R.id.Header);
        int val = getIntent().getExtras().getInt("Pos");
        recipeslct = val;
        header.setText(MainActivity.recipeCollect.get(val).getName());
        header.setTextSize(30);
        insView = (RecyclerView)findViewById(R.id.ins_view);
        ingView = (RecyclerView)findViewById(R.id.ing_view);
        instructionAdapter = new InstructionAdapter(this,MainActivity.recipeCollect.get(val).getInstructions());
        ingAdapter = new IngredientsAdapter(this,MainActivity.recipeCollect.get(val).getIngredients());
        ingView.setAdapter(ingAdapter);
        insView.setAdapter(instructionAdapter);
        ingLayoutMang = new LinearLayoutManager(this);
        insLayoutMang = new LinearLayoutManager(this);
        ingView.setLayoutManager(ingLayoutMang);
        insView.setLayoutManager(insLayoutMang);
        float offset = getResources().getDimension(R.dimen.bottmOffsetter);
        BottomOffsetDec bottomOffsetDec =  new BottomOffsetDec((int) offset);
        insView.addItemDecoration(bottomOffsetDec);
        ingView.addItemDecoration(new DividerItemDecoration(this,ResourcesCompat.getDrawable(getResources(),R.drawable.divider_background,null)));
        insView.addItemDecoration(new DividerItemDecoration(this,ResourcesCompat.getDrawable(getResources(),R.drawable.divider_background,null)));
        detFragmentManager = this.getSupportFragmentManager();
        detFragmentTransaction = detFragmentManager.beginTransaction();
        if(detFragmentManager.findFragmentById(R.id.vid_View) == null) {
            detFragmentTransaction.add(R.id.vid_View, new DetailFrag());
        }
        detFragmentTransaction.commit();
        vidNumView = (TextView)findViewById(R.id.vid_number);
        vidNumView.bringToFront();
       //nd,null)));

    }
    @Override
    protected void onResume(){
        insView.setAdapter(instructionAdapter);
        super.onResume();
    }
    private int getLastVisibleItemPositionIns() {
        return insLayoutMang.findLastVisibleItemPosition();
    }
    private void setRecyclerViewScrollListenerIns() {
        insView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = insView.getLayoutManager().getItemCount();

            }
        });
    }

    private int getLastVisibleItemPosition() {
        return ingLayoutMang.findLastVisibleItemPosition();
    }
    private void setRecyclerViewScrollListener() {
        ingView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = ingView.getLayoutManager().getItemCount();

            }
        });
    }
    private void setInsRecyclerViewScrollListener() {
        insView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("hello ", String.valueOf(getLastVisibleItemPositionIns()) );

            }
        });
    }


}
