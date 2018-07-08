package com.example.rajadurai.group2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);

        String helptext = "This application is used to detect machine images using Google AR. \n" +
                " To use this application you used press the Detect button which will to camera view." +
                " Scan the machine image and if the image matches you will be given information about the machine.";
        TextView t = (TextView) findViewById(R.id.helptextview);
        t.setText(helptext);
    }
}
