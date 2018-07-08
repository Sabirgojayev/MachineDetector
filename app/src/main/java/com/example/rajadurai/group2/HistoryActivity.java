package com.example.rajadurai.group2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.rajadurai.group2.PrefSingleton;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history__activity);
        PrefSingleton.editSharePrefs("machineOne", "3DPrinter", getApplicationContext());
        String MachineName = PrefSingleton.getSharePrefs("machineOne", getApplicationContext());

        TextView t = (TextView) findViewById(R.id.histextview);
        System.out.println(MachineName);
        t.setText("I am text"+ MachineName);
    }
}
