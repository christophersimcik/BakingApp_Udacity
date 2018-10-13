package com.example.csimcik.bakingapp;

import java.util.ArrayList;

public class Recipe {
    public String name;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Instruction> instructions;
    public String servings;
    public Recipe(String nameA,String servingsA,ArrayList<Ingredient> ingredientsA,ArrayList<Instruction> instructionsA){
        this.name = nameA;
        this.servings = servingsA;
        this.ingredients = ingredientsA;
        this.instructions = instructionsA;

    }
    public String getName(){
        return name;
    }
    public String getServings(){
        return servings;
    }
    public ArrayList getIngredients(){
        return ingredients;
    }
    public ArrayList getInstructions(){
        return instructions;
    }

    }

