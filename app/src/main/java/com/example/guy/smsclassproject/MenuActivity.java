package com.example.guy.smsclassproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    private Button toDrafts;
    private Button toHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toDrafts = (Button) findViewById(R.id.goToDrafts);
        toDrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                goToDrafts();
            }
        });
        toHistory = (Button) findViewById(R.id.goToTextHistory);
        toHistory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                goToTextHistory();
            }
        });
    }
    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    public void goToDrafts( ){
        startActivity(new Intent(MenuActivity.this, DraftsActivity.class));
    }

    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    public void goToTextHistory(){
        startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
    }
}
