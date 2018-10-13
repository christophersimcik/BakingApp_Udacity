package com.example.csimcik.bakingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by csimcik on 6/13/2017.
 */
public class RecipeGetter extends AsyncTask<Void,Void,String> {
    public static JSONArray json;
    private Context mContext;
    public RecipeGetter (Context context){
        mContext = context;
    }



    @Override
    protected void onPreExecute(){

    }
    @Override
    protected void onPostExecute(String result){
        if(result == null){
            result = "Error - No String";
        }
        json = null;
        try {
            json = new JSONArray(result);
            new JSONParser(json,mContext).parseJsonObj();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i <MainActivity.recipeCollect.size(); i++){
            Log.i("Recipe "+ i + " ", MainActivity.recipeCollect.get(i).getName());
            for(int j = 0; j < MainActivity.recipeCollect.get(i).getIngredients().size(); j ++ ) {
                ArrayList<Ingredient> tempIng = MainActivity.recipeCollect.get(i).getIngredients();
                Log.i("ingredient " + j+ " ", tempIng.get(j).getTypes());
            }
        }

        Log.i("String ", result);

    }
    @Override
    protected String doInBackground(Void...urls) {
        try {
            URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }

        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }
}
