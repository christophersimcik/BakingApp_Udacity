package com.example.csimcik.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;


class StackRemoteViewsFactoryDetail implements RemoteViewsService.RemoteViewsFactory {
    private static final int mCount = 10;
    private List<Ingredient> mIngItems = new ArrayList<Ingredient>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactoryDetail(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        mIngItems = MainActivity.recipeCollect.get(StackWidgetProvider.viewIndex).getIngredients();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        mIngItems.clear();
    }

    public int getCount() {
        return mIngItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_ing);
        rv.setTextViewText(R.id.quantity, (CharSequence) mIngItems.get(position).getQuantity());
        rv.setTextViewText(R.id.unit, (CharSequence) mIngItems.get(position).getMeasure());
        rv.setTextViewText(R.id.type, (CharSequence) mIngItems.get(position).getTypes());
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
       //mIngItems = MainActivity.recipeCollect.get(StackWidgetProvider.viewIndex).getIngredients();
    }
}