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
    private Button toTexting;
    @Override
    /**
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //The button is retrieved from the layout.
        toDrafts = (Button) findViewById(R.id.goToDrafts);
        //A listener is added to the button.
        toDrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                goToDrafts();
            }
        });
        toHistory = (Button) findViewById(R.id.goToTextHistory);
        toHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToTextHistory();
            }
        });
        toTexting = (Button) findViewById(R.id.textButton);
        toTexting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                goToTexting();
            }
        });
    }

    /**
     * Start a new Activity "DraftsActivity" and takes the user there
     */
    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    public void goToDrafts( ){
        //This one line will take the user from the MenuActivity to the Drafts activity.
        startActivity(new Intent(MenuActivity.this, DraftsActivity.class));
    }

    /**
     * Start a new Activity "HistoryActivity" and takes the user there.
     */

    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    public void goToTextHistory(){
        startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
    }

    /**
     * Start a new Activity "TextingActivity", and takes the user to there.
     *
     */
    public void goToTexting(){
        startActivity(new Intent(MenuActivity.this, TextingActivity.class));
    }
}
