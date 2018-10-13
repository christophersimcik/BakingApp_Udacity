package com.example.csimcik.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final int mCount = 10;
    private List<Recipe> mWidgetItems = new ArrayList<Recipe>();
    private Context mContext;
    public static int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    public void onCreate() {
        if(MainActivity.recipeCollect.size() > 0 || !MainActivity.recipeCollect.isEmpty()) {
            Log.i("list ","has stuff");
            mWidgetItems = MainActivity.recipeCollect;
       }else{
            try {
                new RecipeGetter(this.mContext).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mWidgetItems = MainActivity.recipeCollect;
            Log.i("list ","has no stuff running rec get");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        mWidgetItems.clear();
        MainActivity.recipeCollect.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item, (CharSequence) mWidgetItems.get(position).getName());
        Intent intent = new Intent();
        intent.setAction("com.example.broadcast.MY_NOTIFICATION");
        intent.putExtra("data","Notice me senpai!");
        Bundle extras = new Bundle();
        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {

    }
}