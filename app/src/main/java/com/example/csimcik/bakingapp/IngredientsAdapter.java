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

import java.util.ArrayList;

/**
 * Created by csimcik on 6/13/2017.
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
ArrayList<Ingredient> recipeList = new ArrayList<>();
    Context aContext;
    public IngredientsAdapter(Context mContext, ArrayList<Ingredient>recipeListA){
    recipeList = recipeListA;
         aContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingred_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient itemIng = recipeList.get(position);
        holder.setQuant(itemIng.getQuantity());
        holder.setMeas(itemIng.getMeasure());
        holder.setType(itemIng.getTypes());
        Log.i("this is it", "ok");

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView typeView;
        private TextView quantityView;
        private TextView unitView;

        public ViewHolder(View itemView) {
            super(itemView);
             quantityView = (TextView)itemView.findViewById(R.id.quantity);
             unitView = (TextView)itemView.findViewById(R.id.unit);
             typeView = (TextView)itemView.findViewById(R.id.type);
             itemView.setOnClickListener(this);
        }
        public void setQuant(String text){
            quantityView.setText(text);
        }
        public void setMeas(String text){
            unitView.setText(text);
        }
        public void setType(String text){
            typeView.setText(text);
        }
        @Override
        public void onClick(View v) {
            Log.i("Recipedapt ", "Clicked" + " " + getAdapterPosition());
        }
    }
}
