package com.example.rajadurai.group2;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class PrefSingleton {

    public static final String machineList = "machineList";
    public static final String MACHINE_LIST_KEY = "machines";

    public static void editSharePrefs(String key, String value, Context act) {
        SharedPreferences.Editor editor = act.getSharedPreferences(machineList, Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = act.getSharedPreferences(machineList, Context.MODE_PRIVATE);
        Set<String> machines = new HashSet<String>(prefs.getStringSet(key, new HashSet<String>()));
        machines.add(value);
        editor.putStringSet(key, machines);
        editor.apply();
        System.out.println(machines);
        System.out.println(prefs.getStringSet(key, new HashSet<String>()));
    }

    public static Set<String> getSharePrefs(String key, Context act) {
        SharedPreferences prefs = act.getSharedPreferences(machineList, Context.MODE_PRIVATE);
        return prefs.getStringSet(key, new HashSet<String>());
    }

    public static void removeMachineFromHistory (String key, String value, Context act) {
        SharedPreferences.Editor editor = act.getSharedPreferences(machineList, Context.MODE_PRIVATE).edit();
        SharedPreferences prefs = act.getSharedPreferences(machineList, Context.MODE_PRIVATE);
        HashSet<String> machines = new HashSet<String>(prefs.getStringSet(key, new HashSet<String>()));
        machines.remove(value);
        editor.remove(key);
        editor.putStringSet(key, machines);
        editor.apply();
    }

    public static void removeAllMachines(String key, Context act) {
        SharedPreferences.Editor editor = act.getSharedPreferences(machineList, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }
}