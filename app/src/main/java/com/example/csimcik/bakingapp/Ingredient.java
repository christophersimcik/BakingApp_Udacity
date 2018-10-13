package com.example.csimcik.bakingapp;

import java.util.ArrayList;

/**
 * Created by csimcik on 6/14/2017.
 */
public class Ingredient {
    String quantity;
    String types;
    String measure;
    public Ingredient(String quantityA, String typesA, String measureA){
        this.quantity = quantityA;
        this.types = typesA;
        this.measure = measureA;
    }
    public String getQuantity(){
        return quantity;
    }
    public String getTypes(){
        return types;
    }
    public String getMeasure(){
        return measure;
    }
}
