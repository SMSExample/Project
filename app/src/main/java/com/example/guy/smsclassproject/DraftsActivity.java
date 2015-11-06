package com.example.guy.smsclassproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.*;
import java.util.*;

public class DraftsActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText searchText;
    private Button prevButton;
    private Button nextButton;
    DraftsDatabase draftsDatabase;
    Button[] draftButtons;
    private int page;
    private TextView pageNumber;
    ArrayList<MessageObject> messagesToBeDisplayed;

    @Override
    /**
     * actions done when the activity starts
     * @param savedInstanceState android class
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);
        draftsDatabase = new DraftsDatabase();
        messagesToBeDisplayed = draftsDatabase.getAllTexts();
        searchButton = (Button)findViewById(R.id.searchButton);
        searchText = (EditText)findViewById(R.id.searchText);
        prevButton = (Button)findViewById(R.id.prevButton);
        nextButton = (Button)findViewById(R.id.nextButton);
        pageNumber = (TextView)findViewById(R.id.pageNumber);
        searchButton.setOnClickListener(new MessageOnClickListener());
        prevButton.setOnClickListener(new MessageOnClickListener());
        nextButton.setOnClickListener(new MessageOnClickListener());
        initializeMessageButtons();
        redisplayTexts();

    }

    /**
     * calls search from database class and displays the results
     */
    private void search()
    {
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        page=0;
        redisplayTexts();
    }

    /**
     * sets up all the buttons used for messages
     */
    private void initializeMessageButtons()
    {
        draftButtons = new Button[10];
        draftButtons[0] = (Button)findViewById(R.id.textButton1);
        draftButtons[1] = (Button)findViewById(R.id.textButton2);
        draftButtons[2] = (Button)findViewById(R.id.textButton3);
        draftButtons[3] = (Button)findViewById(R.id.textButton4);
        draftButtons[4] = (Button)findViewById(R.id.textButton5);
        draftButtons[5] = (Button)findViewById(R.id.textButton6);
        draftButtons[6] = (Button)findViewById(R.id.textButton7);
        draftButtons[7] = (Button)findViewById(R.id.textButton8);
        draftButtons[8] = (Button)findViewById(R.id.textButton9);
        draftButtons[9] = (Button)findViewById(R.id.textButton10);
        for(Button button : draftButtons)
        {
            button.setOnClickListener(new MessageOnClickListener());
        }
    }
    /**
     * Checks what button is clicked and responds accordingly
     */
    private class MessageOnClickListener implements View.OnClickListener {
        public void onClick(View v)
        {
           if(v==searchButton)
           {
                search();
               return;
           }
            if(v==prevButton)
           {
               if(page<=0)
                   return;
               page--;
               redisplayTexts();
               return;
           }
            if(v==nextButton)
           {
               if((messagesToBeDisplayed.size()-1)/10<=page)
                   return;
               page++;
               redisplayTexts();
               return;
           }
            int loc = 0;
            for(; loc<10; loc++)
            {
                if(draftButtons[loc]==v)
                    break;
            }
            int draftIndex = loc+10*page;
            if(draftIndex>=messagesToBeDisplayed.size())
                return;
            MessageObject continueMessage = messagesToBeDisplayed.get(draftIndex);
            draftsDatabase.deleteMessage(continueMessage);
            TextingActivity.continueMessage(continueMessage);
            page=0;
            redisplayTexts();
            startActivity(new Intent(DraftsActivity.this, TextingActivity.class));




        }
    }
    /**
     * displays drafts in groups of ten
     */
    private void redisplayTexts()
    {

        int amountOfMessages = messagesToBeDisplayed.size();
        for(int i = 0; i<10; i++)
        {
            if(i+10*page>=amountOfMessages)
            {
                clearTheRest(i);
                break;
            }
            MessageObject message = messagesToBeDisplayed.get(i+10*page);
            String str = message.getNumber()+": ";
            str +=message.getSmsMessage();
            draftButtons[i].setText(str);
        }
        pageNumber.setText(""+(page+1));
    }
    /**
     * clears text from buttons, mostly used when going to a new
     * page of results
     * @param startButton button to start clearing from
     */
    private void clearTheRest(int startButton)
    {
        for(int i =  startButton; i<10; i++)
        {
            draftButtons[i].setText("");
        }
    }


}
