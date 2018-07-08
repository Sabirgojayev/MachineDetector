package com.example.rajadurai.group2;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.HashMap;


public class PrefSingleton {

    public static final String machineList = "machineList";

    public static void editSharePrefs(String key, String value, Context act) {
        SharedPreferences.Editor editor = act.getSharedPreferences(machineList, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
        System.out.println("I am setting the value");
    }

    public static String getSharePrefs(String key, Context act) {
        SharedPreferences prefs = act.getSharedPreferences(machineList, Context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public static Map<String, String> getAllpreferences(Context act) {
        SharedPreferences prefs = act.getSharedPreferences(machineList, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        HashMap<String, String> machinevalues = new HashMap<String, String>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            machinevalues.put(entry.getKey(), entry.getValue().toString());
        }
        return machinevalues;
    }
}