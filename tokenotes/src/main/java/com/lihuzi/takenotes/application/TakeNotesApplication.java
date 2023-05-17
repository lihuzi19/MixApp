package com.lihuzi.takenotes.application;

import android.app.Application;

/**
 * Created by cocav on 2017/11/13.
 */

public class TakeNotesApplication extends Application
{
    private static Application _instance;

    public static Application getInstance()
    {
        return _instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        _instance = this;
    }
}
