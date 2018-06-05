package com.pihrit.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferencesUtil<T> {

    private static final String JOIN_DELIMITER = "@@@";
    private SharedPreferences prefs;


    public SharedPreferencesUtil(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Stores the given objects to provided key
     *
     * @param key     - Key for storing to SharedPreferences
     * @param objects - Objects that you like to store
     */
    public void storeObjects(String key, ArrayList<?> objects) {
        Gson gson = new Gson();
        ArrayList<String> objectsAsJson = new ArrayList<>();
        for (Object o : objects) {
            objectsAsJson.add(gson.toJson(o));
        }
        prefs.edit().putString(key, TextUtils.join(JOIN_DELIMITER, objectsAsJson.toArray())).apply();
    }

    /**
     * Loads stored objects with the given key
     *
     * @param key
     * @param clazz
     * @return
     */
    public ArrayList<Object> loadObjects(String key, Class<?> clazz) {
        Gson gson = new Gson();

        ArrayList<String> objectsAsJson = new ArrayList<>(Arrays.asList(TextUtils.split(prefs.getString(key, ""), JOIN_DELIMITER)));
        ArrayList<Object> objects = new ArrayList<>();

        for (String oJson : objectsAsJson) {
            objects.add(gson.fromJson(oJson, clazz));
        }
        return objects;
    }

    /**
     * Stores the given object with the given key
     *
     * @param key
     * @param value
     */
    public void storeObject(String key, T value) {
        Gson gson = new Gson();
        prefs.edit().putString(key, gson.toJson(value)).apply();
    }

    public Object getObject(String key, Class<?> clazz) {
        Gson gson = new Gson();
        String prefsString = prefs.getString(key, "");
        return gson.fromJson(prefsString, clazz);
    }

}
