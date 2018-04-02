package com.dk.watercup;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
<<<<<<< HEAD
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.Help);
=======
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.Help);
>>>>>>> 9c7c6c8dfa6ac6ef18b1be27acecbda9a91f5642
    }
}
