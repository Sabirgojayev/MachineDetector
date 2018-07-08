package com.example.rajadurai.group2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {
    HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
    TextView name;
    ListView videosList;
    ListView documentList;
    ArrayAdapter videoAdapter;
    ArrayAdapter documentAdapter;
    File file;
    private final  int Image_Info = 0;
    private final  int Name_Info = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        createLists();
        PrefSingleton.editSharePrefs(PrefSingleton.MACHINE_LIST_KEY, items.get("name").get(0), this);
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
    private void getMachineInfo(String value, int mode) {

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
                    if (mode == Image_Info) {
                        if (getValueByImage(value, element2)) {
                            addMachineInfo("file", element2);
                            addMachineInfo("name", element2);
                            addMachineInfo("video", element2);
                            break;
                        }
                    } else {
                        if (getValueByName(value, element2)) {
                            addMachineInfo("file", element2);
                            addMachineInfo("name", element2);
                            addMachineInfo("video", element2);
                                break;
                        }
                    }
                }
            }//end of for loop

        } catch (Exception e) {e.printStackTrace();}
    }

    private boolean getValueByImage(String imageName, Element element) {
        NodeList nodeList = element.getElementsByTagName("image");
        for (int i=0; i<nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (node.getTextContent().equals(imageName)) {
                return true;
            }
        }
        return false;
    }
    private boolean getValueByName(String machineName, Element element) {
        NodeList nodeList = element.getElementsByTagName("name");
        for (int i=0; i<nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            if (node.getTextContent().equals(machineName)) {
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

    private void createLists() {
        Intent intent = getIntent();
        if (intent.getStringExtra("imageName") != null){
        String image = intent.getStringExtra("imageName");
        getMachineInfo(image, Image_Info);
        } else {
            String machine_name = intent.getStringExtra("machine");
            getMachineInfo(machine_name, Name_Info);
        }
        name = (TextView) findViewById(R.id.machine_name);
        name.setText(items.get("name").get(0));
        videosList=(ListView)findViewById(R.id.videoview);
        documentList=(ListView)findViewById(R.id.documentview);
        videoAdapter = new ArrayAdapter(AboutActivity.this, R.layout.video_item, items.get("video"));
        documentAdapter = new ArrayAdapter(this, R.layout.document_item, items.get("file"));
        videosList.setAdapter(videoAdapter);
        documentList.setAdapter(documentAdapter);
        ListUtils.setDynamicHeight(videosList);
        ListUtils.setDynamicHeight(documentList);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Changes 'back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }


}