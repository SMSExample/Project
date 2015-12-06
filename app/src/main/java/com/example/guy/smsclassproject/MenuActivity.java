package com.example.guy.smsclassproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private Button toDrafts;
    private Button toHistory;
    private Button toTexting;
    private Button toConversations;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:{
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){;}
                else{;}
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){;}
                else{;}
                return;
            }
        }
    }

    @Override
    /**
     * Find all the Views of the MenuActivity.xml and give all the Buttons their
     * onClickListeners
     * @Param savedInstanceState The Bundle that is needed to call the super.onCreate().
     */
    protected void onCreate(Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECEIVE_SMS)){;}
            else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECEIVE_SMS},MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)){;}
            else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
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
            public void onClick(View v) {

                goToTexting();
            }
        });
        toConversations = (Button) findViewById(R.id.goToConversations);
        toConversations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                goToConversations();
            }
        });
    }
    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    /**
     * Start a new Activity "DraftsActivity" and takes the user there
     */
    public void goToDrafts( ){
        //This one line will take the user from the MenuActivity to the Drafts activity.
        startActivity(new Intent(MenuActivity.this, DraftsActivity.class));
    }

    //called when user touch the button, learned from http://developer.android.com/guide/topics/ui/controls/button.html
    /**
     * Start a new Activity "HistoryActivity" and takes the user there.
     */
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

    public void goToConversations()
    {
        startActivity(new Intent(MenuActivity.this, ConversationActivity.class));
    }
}
