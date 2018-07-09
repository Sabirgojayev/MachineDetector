package com.example.rajadurai.group2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);

        String helptext = "This application is used to detect machine images using Google AR. \n \n" +
                "To start with this application you need to press the \'Detect\' button which leads to the camera view." +
                " Scan the machine image and if the image matches, the application will give information about the machine detected." +
                "\n \n" +
                "Press on \'History\' button to view the list of machines detected by the application." +
                " From here you can also navigate back to the page which has information about the machine. " ;
        TextView t = (TextView) findViewById(R.id.helptextview);
        t.setText(helptext);
    }
}