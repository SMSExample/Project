package com.example.guy.smsclassproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    Button[] messageButtons;
    Button searchButton;
    Button prevButton;
    Button nextButton;
    EditText searchBar;
    TextView pageNumber;
    int page;
    static ArrayList<MessageObject> messagesToBeDisplayed;
    MessageDatabase messageDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        page = 0;
        searchButton = (Button)findViewById(R.id.searchButton);
        prevButton = (Button)findViewById(R.id.pre);
        nextButton = (Button)findViewById(R.id.next);
        searchBar = (EditText)findViewById(R.id.searchKeyWord);
        pageNumber = (TextView)findViewById(R.id.pageNumber);
        searchButton.setOnClickListener(new MessageOnClickListener());
        prevButton.setOnClickListener(new MessageOnClickListener());
        nextButton.setOnClickListener(new MessageOnClickListener());
        messageDatabase = new MessageDatabase();

        initializeMessageButtons();
        messagesToBeDisplayed = messageDatabase.getAllTexts();
        redisplayTexts();

    }
    private void initializeMessageButtons(){
        messageButtons = new Button[10];
        messageButtons[0] = (Button)findViewById(R.id.Button1);
        messageButtons[1] = (Button)findViewById(R.id.Button2);
        messageButtons[2] = (Button)findViewById(R.id.Button3);
        messageButtons[3] = (Button)findViewById(R.id.Button4);
        messageButtons[4] = (Button)findViewById(R.id.Button5);
        messageButtons[5] = (Button)findViewById(R.id.Button6);
        messageButtons[6] = (Button)findViewById(R.id.Button7);
        messageButtons[7] = (Button)findViewById(R.id.Button8);
        messageButtons[8] = (Button)findViewById(R.id.Button9);
        messageButtons[9] = (Button)findViewById(R.id.Button10);
        for(int x = 0; x<10; x++){
            messageButtons[x].setOnClickListener(new MessageOnClickListener());
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
                if(messageButtons[loc]==v)
                    break;
            }
            int draftIndex = loc+10*page;
            if(draftIndex>=messagesToBeDisplayed.size())
                return;

            MessageObject histroyMessage = messagesToBeDisplayed.get(draftIndex);
            SingleTextActivity.setMessage(histroyMessage);
            SingleTextActivity.deleteFromList(messagesToBeDisplayed);
            startActivity(new Intent(HistoryActivity.this, SingleTextActivity.class));





        }
    }
    private void search()
    {
        messagesToBeDisplayed=messageDatabase.getMessagesByKey(searchBar.getText().toString());
        page=0;
        redisplayTexts();
    }

    /**
     * it changes the text of all the buttons
     */
    public void redisplayTexts()
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
            String str;
            if(message.wasSentByUser())
                str ="You: ";
            else
                str = message.getNumber()+": ";
            str +=message.getSmsMessage();
            messageButtons[i].setText(str);
        }
        pageNumber.setText("" + (page + 1));
    }
    private void clearTheRest(int startButton)
    {
        for(int i =  startButton; i<10; i++)
        {
            messageButtons[i].setText("");
        }
    }
    protected void onResume()
    {
        if(messageButtons!=null)
        redisplayTexts();
        super.onResume();
    }
}
