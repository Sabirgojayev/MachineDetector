package com.example.rajadurai.group2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AboutActivity extends AppCompatActivity {
    HashMap<String, String> items = new HashMap<String, String>();
    TextView videoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String image = intent.getStringExtra("imageName");
        getMachineInfo(image);
        Toast.makeText(this, items.toString(), Toast.LENGTH_LONG).show();
        videoLink = (TextView) findViewById(R.id.video);
        videoLink.setText(items.get("video"));
        Toast.makeText(this, items.get("video"), Toast.LENGTH_SHORT).show();
        videoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = (String) videoLink.getText();
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(link));
                startActivity(webIntent);
            }
        });

    }
    private void getMachineInfo(String image) {

        try {
            InputStream is = getAssets().open("sample.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("employee");
            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    if (getValue(image, element2)) {
                        addMachineInfo("file", element2);
                        addMachineInfo("name", element2);
                        addMachineInfo("video", element2);
                        break;
                    }
                }
            }//end of for loop

        } catch (Exception e) {e.printStackTrace();}
    }

    private boolean getValue(String imageName, Element element) {
        NodeList nodeList = element.getElementsByTagName("image");
        for (int i=0; i<nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (node.getTextContent().equals(imageName)) {
                return true;
            }
        }
        return false;
    }
    private void addMachineInfo(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            items.put(tag, node.getTextContent());
        }
    }


}
