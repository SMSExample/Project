package com.example.guy.smsclassproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ZoomControls;

import java.util.ArrayList;

public class SingleTextActivity extends AppCompatActivity {

    private static MessageObject theMessage;
    private TextView singleText;
    private MessageDatabase messageDatabase;
    private Button deleteButton;
    private ZoomControls zoomControls;
    private float textSize;
    static private ArrayList<MessageObject> deleteFromThisList;

    @Override
    /**
     * actions done when the activity starts
     * @param savedInstanceState android class
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_text);
        if(theMessage==null)
            theMessage=new MessageObject("","",false);
        singleText = (TextView) findViewById(R.id.viewMessage);
        singleText.setText(theMessage.getSmsMessage());
        messageDatabase = new MessageDatabase();
        deleteButton = (Button)findViewById(R.id.deleteButton);
        zoomControls = (ZoomControls)findViewById(R.id.zoomControls);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteMessage();
            }
        });
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                zoomOut();
            }
        });
        textSize = singleText.getTextSize();
    }

    @Override
    /**
     * adds items to android menu
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_text, menu);
        return true;
    }

    @Override
    /**
     * handles input for an action bar
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * displays the message string of a given message object on screen
     * @param mO the message object
     */
    public static void setMessage(MessageObject mO)
    {
        theMessage = mO;
    }

    /**
     * on screen feedback for message deletion
     */
    private void deleteMessage()
    {
        messageDatabase.deleteMessage(theMessage);
        if(deleteFromThisList!=null)
        {
            deleteFromThisList.remove(theMessage);
            deleteFromThisList=null;
        }
        singleText.setText("Message Deleted!");

    }

    /**
     * calls the delete method from the appropriate database to remove the message
     */
    public static void deleteFromList(ArrayList<MessageObject> listOfMessages)
    {
        deleteFromThisList = listOfMessages;
    }

    /**
     * makes text larger when user hits zoom in
     */
    private void zoomIn()
    {
        textSize*=1.2;
        singleText.setTextSize(textSize);
    }

    /**
     * makes text smaller when user hits zoom out
     */
    private void zoomOut()
    {
        textSize*=.8;
        singleText.setTextSize(textSize);
    }
}
