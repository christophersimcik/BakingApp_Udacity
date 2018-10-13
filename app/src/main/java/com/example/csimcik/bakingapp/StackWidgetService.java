package com.example.csimcik.bakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by csimcik on 7/7/2017.
 */
public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("This is working", " Ya SEEEEEEEEEEEE!");
        if(StackWidgetProvider.situ == 0) {
            Log.i("Check Whch running", "StackRemoteViewsFactory");
            return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
        }else{
            Log.i("Check Whch running", "StackRemoteViewsFactoryDetail");
            return new StackRemoteViewsFactoryDetail(this.getApplicationContext(), intent);
        }
    }
}
