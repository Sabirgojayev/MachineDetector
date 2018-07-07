package com.example.rajadurai.group2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class AboutActivity extends AppCompatActivity {

    TextView videoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        //videoLink = (TextView) findViewById(R.id.videoLink);

        /*videoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = videoLink.getText();
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(link));
                startActivity(webIntent);
            }
        });*/

    }



}
