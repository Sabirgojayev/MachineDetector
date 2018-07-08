package com.example.rajadurai.group2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.rajadurai.group2.PrefSingleton;

import java.util.HashSet;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history__activity);
        HashSet<String> machines = (HashSet<String>) PrefSingleton.getSharePrefs(PrefSingleton.MACHINE_LIST_KEY, this);
        
    }
}
