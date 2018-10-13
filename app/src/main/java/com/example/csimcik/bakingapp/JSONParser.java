package com.example.csimcik.bakingapp;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by csimcik on 6/14/2017.
 */
public class JSONParser {
    public JSONArray jsonArray;
    public Context context;

    public JSONParser(JSONArray jsonArrayA, Context context) {
        this.context = context;
        this.jsonArray = jsonArrayA;
    }
    public void parseJsonObj() throws JSONException {
        try {

            for(int i = 0; i < jsonArray.length();i ++){
                ArrayList<Ingredient> ingredientsArray = new ArrayList<>();
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                ArrayList<Instruction> instructionsArray = new ArrayList<>();
                // get Recipe Obj data
                String recName = jsonObj.getString("name");
                String servings = jsonObj.getString("servings");
                JSONArray ingredientsList =  jsonObj.getJSONArray("ingredients");
                JSONArray stepsList =  jsonObj.getJSONArray("steps");
                // create an arrayList of Ingredient Obj
                for(int j = 0; j < ingredientsList.length(); j++){
                    JSONObject jsonIng = ingredientsList.getJSONObject(j);
                    ingredientsArray.add( new Ingredient(jsonIng.getString("quantity"),jsonIng.getString("ingredient"),jsonIng.getString("measure")));
                }
                // create an arrayList of istruction Obj
                for(int k = 0; k < stepsList.length(); k ++){
                    JSONObject jsonInst = stepsList.getJSONObject(k);
                    instructionsArray.add( new Instruction(jsonInst.getString("id"),jsonInst.getString("shortDescription"), jsonInst.getString("description"),jsonInst.getString("videoURL"), jsonInst.getString("thumbnailURL")));
                }

            MainActivity.recipeCollect.add(new Recipe(recName,servings,ingredientsArray,instructionsArray));
                if(MainActivityFragment.mainAdapter != null) {
                    MainActivityFragment.mainAdapter.notifyDataSetChanged();
                }
                    if(StackWidgetProvider.mgr != null) {
                        AppWidgetManager appWidgetManager = StackWidgetProvider.mgr;
                        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                                new ComponentName(context, StackWidgetProvider.class));
                        for(int h = 0; h < appWidgetIds.length; h++) {
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[h], R.id.stack_view);
                            appWidgetManager.updateAppWidget(appWidgetIds[h], StackWidgetProvider.mainView);
            }
        }

                Log.i("check ", String.valueOf(MainActivity.recipeCollect.size()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

