package com.example.csimcik.bakingapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by csimcik on 6/22/2017.
 */
public class CustomLayoutMan extends LinearLayoutManager {
    private int mParentWidth;
    private int mItemWidth;
    public CustomLayoutMan(Context context, int parentWidth, int itemWidth) {
        super(context);
        mParentWidth = parentWidth;
        mItemWidth = itemWidth;
    }
    @Override
    public int getPaddingLeft() {
        return Math.round(mParentWidth / 2f - mItemWidth / 2f);
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}
