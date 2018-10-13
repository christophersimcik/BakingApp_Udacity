package com.example.csimcik.bakingapp;

import java.util.ArrayList;

/**
 * Created by csimcik on 6/14/2017.
 */
public class Instruction {
    String id;
    String shortDesc;
    String longDesc;
    String vidUrl;
    String thumb;
    public Instruction( String idA,  String shortDescA,  String longDescA, String vidUrlA, String thumbA){
        this.id = idA;
        this.shortDesc = shortDescA;
        this.longDesc =  longDescA;
        this.vidUrl = vidUrlA;
        this.thumb = thumbA;
    }
    public String getId(){
        return id;
    }
    public String getShortDesc(){
        return shortDesc;
    }
    public String getLongDesc(){
        return longDesc;
    }
    public String getVidUrl(){
        return vidUrl;
    }
    public String getThumb(){
        return thumb;
    }
}
