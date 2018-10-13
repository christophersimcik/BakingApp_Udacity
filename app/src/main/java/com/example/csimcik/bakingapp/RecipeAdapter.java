package com.example.csimcik.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by csimcik on 6/13/2017.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
ArrayList<Recipe> recipeList = new ArrayList<>();
    Context aContext;
    public RecipeAdapter(Context mContext, ArrayList<Recipe>recipeListA){
    recipeList = recipeListA;
        aContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe itemRec = recipeList.get(position);
        holder.setRecipeName(itemRec.getName());

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.mytextView);
            itemView.setOnClickListener(this);
        }
        public void setRecipeName(String text){
            textView.setText(text);
            textView.setHeight((int)MainActivityFragment.adjHeight);
            textView.setTextSize(25);
            textView.setPadding(0,0,0,0);
        }
        @Override
        public void onClick(View v) {
            Log.i("Recipedapt ", "Clicked" + " " + getAdapterPosition());
            Intent detailView = new Intent(aContext,Detail.class);
            Bundle bundle = new Bundle();
            detailView.putExtra("Pos", getAdapterPosition());
            bundle.putInt("POS", getAdapterPosition());
            aContext.startActivity(detailView);
        }
    }
}
