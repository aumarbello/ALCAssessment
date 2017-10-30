package com.example.ahmed.alcassessment.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.example.ahmed.alcassessment.R;

import javax.inject.Inject;

/**
 * Created by ahmed on 10/26/17.
 */

public class Prefs {
    private static SharedPreferences preferences;
    private static Resources resources;
    @Inject
    Prefs(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        resources = context.getResources();
    }

    public boolean createRandomCard(){
        return preferences.getBoolean(resources.getString
                (R.string.preload_random_key), false);
    }

    public boolean twoWayExchange(){
        return preferences.getBoolean(resources.getString(R.string.two_way_key),
                true);
    }

    public int numberOfCards(){
        return Integer.valueOf(preferences.getString(resources.getString
                (R.string.preload_number_key), "8"));
    }

    public int getListType(){
        return Integer.valueOf(preferences.getString(resources.getString
                (R.string.list_layout_key), "1"));
    }

    public boolean refreshAll(){
        return preferences.getBoolean(resources.getString(R.string.key_refresh_all), false);
    }
}
