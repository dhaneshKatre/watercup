package com.dk.watercup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.Help);
    }
}
