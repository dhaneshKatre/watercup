package com.dk.watercup;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAQActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

    }
    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<String, List<String>>();

        listDataHeader.add("utkarsh");
        listDataHeader.add("viraj");
        listDataHeader.add("shubham");
        listDataHeader.add("dhanno");

        List<String> friends  = new ArrayList<>();
        friends.add("this is a Expandable listView");

        List<String> ump = new ArrayList<>();
        ump.add("Expandable Listview");
        ump.add("Googlemaps");
        ump.add("Chat Application");
        ump.add("Firebase");
    }
}
