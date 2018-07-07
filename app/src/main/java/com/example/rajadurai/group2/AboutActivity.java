package com.example.rajadurai.group2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AboutActivity extends AppCompatActivity {
    HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
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
        TextView name = (TextView) findViewById(R.id.machine_name);
        name.setText(items.get("name").get(0));
        ListView videosList=(ListView)findViewById(R.id.videoview);
        ListView documentList=(ListView)findViewById(R.id.documentview);
        final ArrayAdapter videoAdapter = new ArrayAdapter(AboutActivity.this, R.layout.video_item, items.get("video"));
        final ArrayAdapter documentAdapter = new ArrayAdapter(this, R.layout.document_item, items.get("file"));
        videosList.setAdapter(videoAdapter);
        documentList.setAdapter(documentAdapter);
        ListUtils.setDynamicHeight(videosList);
        ListUtils.setDynamicHeight(documentList);
        videosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) videoAdapter.getItem(i);
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(value));
                startActivity(webIntent);
            }
        });
        documentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) documentAdapter.getItem(i);
                Intent intent = new Intent(AboutActivity.this, ViewPdfActivity.class);
                intent.putExtra("pdf", value);
                startActivity(intent);
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
        ArrayList<String> nodes = new ArrayList<String>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            nodes.add(node.getTextContent());
        }
        items.put(tag, nodes);
    }


    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
