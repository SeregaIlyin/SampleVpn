package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.di.annotation.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private final SharedPreferences mPref;
    private static final String PREF_FILE_NAME = "samplevpn";

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putData(String name, String value) {
        mPref.edit().putString(name, value).apply();
    }

    public String getData(String name) {
        return mPref.getString(name, null);
    }

}
