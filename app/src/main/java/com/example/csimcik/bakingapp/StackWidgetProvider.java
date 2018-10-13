package com.example.csimcik.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class StackWidgetProvider extends AppWidgetProvider {
    public static final String TOAST_ACTION ="com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM   ="com.example.android.stackwidget.EXTRA_ITEM";
    public static final String RECIPE_ACTION  ="com.example.android.stackwidget.RECIPE_ACTION";
    static public int [] ids;
    static RemoteViews rv;
    static RemoteViews mainView;
    static AppWidgetManager mgr;
    static String header;
    static int situ = 0;
    static int viewIndex;

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
       /* int appWidgetId = intent.getIntExtra(mgr.EXTRA_APPWIDGET_ID,
                mgr.INVALID_APPWIDGET_ID);*/

        viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
        switch (intent.getAction()){
            case TOAST_ACTION:
                for(int i = 0; i < ids.length; i++) {
                    situ = 1;
                    Log.i("APPWIDGETID", String.valueOf(ids[i]));
                    //mgr.updateAppWidget(appWidgetId, null);
                    mgr.updateAppWidget(ids[i], updateIngredients(context, ids[i], intent));
                }
                break;
            case RECIPE_ACTION:
                for(int i = 0; i < ids.length; i++) {
                    situ = 0;
                    Log.i("APPWIDGETID", String.valueOf(ids[i]));
                    //mgr.updateAppWidget(appWidgetId, null);
                    mgr.updateAppWidget(ids[i], updateRecipe(context,ids[i]));
                    break;
                }

        }
        super.onReceive(context, intent);
        }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ids =appWidgetIds;
        mgr = AppWidgetManager.getInstance(context);
        for (int i = 0; i < appWidgetIds.length; ++i) {



            Intent intent = new Intent(context, StackWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(R.id.stack_view, intent);
            rv.setEmptyView(R.id.stack_view, R.id.empty_view);


            Intent toastIntent = new Intent(context, StackWidgetProvider.class);
            toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            updateRecipe(context,appWidgetIds[i]);
            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
    public RemoteViews updateRecipe(Context context, int appWidgetId){
        Toast.makeText(context, " UPDATE RECIPE " + viewIndex, Toast.LENGTH_SHORT).show();
        Intent recIntent = new Intent(context,StackWidgetService.class);
        recIntent.putExtra("ID", appWidgetId);
        recIntent.setData(Uri.parse(recIntent.toUri(Intent.URI_INTENT_SCHEME)));
        mainView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        mainView.setRemoteAdapter(R.id.stack_view, recIntent);
        mainView.setEmptyView(R.id.stack_view, R.id.empty_view);
        Intent toastIntent = new Intent(context,StackWidgetProvider.class);
        toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        toastIntent.setData(Uri.parse(toastIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context,0,toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mainView.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);
        return mainView;
    }
    public static RemoteViews updateIngredients(Context context, int appWidgetId, Intent intent){
        viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);

        Toast.makeText(context, " UPDATE INGREDIENTS " + viewIndex, Toast.LENGTH_SHORT).show();
        header = MainActivity.recipeCollect.get(viewIndex).getName();
        Intent detIntent = new Intent(context,StackWidgetService.class);
        ComponentName thisWidget = new ComponentName(context,StackWidgetProvider.class);
        int[] widgIds = mgr.getAppWidgetIds(thisWidget);
        Log.i("numbers of ids ", String.valueOf(widgIds.length));
        detIntent.putExtra("ID", widgIds);
        detIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mainView = new RemoteViews(context.getPackageName(), R.layout.widget_layout_ing);
        mainView.setTextViewText(R.id.recipeHeader,header);
        mainView.setRemoteAdapter(R.id.stack_view_ingred, detIntent);
        Intent backIntent = new Intent(context, StackWidgetProvider.class);
        backIntent.setAction(RECIPE_ACTION);
        backIntent.setData(Uri.parse(backIntent.toUri(Intent.URI_INTENT_SCHEME)));
        backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        backIntent.setAction(RECIPE_ACTION);
        PendingIntent backPendingIntent = PendingIntent.getBroadcast(context, 0, backIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mainView.setOnClickPendingIntent(R.id.backbutton,backPendingIntent);
        mainView.setEmptyView(R.id.stack_view_ingred, R.id.empty_view);
        return mainView;
    }
}