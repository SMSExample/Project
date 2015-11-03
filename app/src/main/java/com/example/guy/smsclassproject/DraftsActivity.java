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
    private void search()
    {
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        page=0;
        redisplayTexts();
    }

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
            MessageObject continueMessage = messagesToBeDisplayed.get(loc+10*page);
            draftsDatabase.deleteMessage(continueMessage);
            TextingActivity.continueMessage(continueMessage);
            page=0;
            redisplayTexts();
            startActivity(new Intent(DraftsActivity.this, TextingActivity.class));




        }
    }
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
    private void clearTheRest(int startButton)
    {
        for(int i =  startButton; i<10; i++)
        {
            draftButtons[i].setText("");
        }
    }
    private int getButtonIndex(Button b)
    {
        for(int x = 0; x<draftButtons.length; x++)
        {
            if(draftButtons[x]==b)
                return x;
        }
        return -1;
    }

}
