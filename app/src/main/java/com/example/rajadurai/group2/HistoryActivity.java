package com.example.rajadurai.group2;

import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.rajadurai.group2.PrefSingleton;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;
import java.util.HashSet;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private MachinesAdapter adapter;
    HashSet<String> machines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history__activity);
        machines = (HashSet<String>) PrefSingleton.getSharePrefs(PrefSingleton.MACHINE_LIST_KEY, this);
        ArrayList<String> machines_history = new ArrayList<String>();
        if (machines != null) {
            machines_history.addAll(machines);
            listView = findViewById(R.id.list_view);
            adapter = new MachinesAdapter(this, machines_history, this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("WTF");
                    String name = (String) adapter.getItem(i);
                    Intent intent = new Intent(HistoryActivity.this, AboutActivity.class);
                    intent.putExtra("machine", name);
                    startActivity(intent);
                }
            });
        }
    }
}
