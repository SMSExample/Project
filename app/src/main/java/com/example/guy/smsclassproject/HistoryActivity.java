package com.example.guy.smsclassproject;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    MessageDatabase messageDatabase;
    Button[] messageButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        messageDatabase = new MessageDatabase();
        ArrayList<MessageObject> allMessages = messageDatabase.getAllTexts();
        initializeMessageButtons();
    }
    private void initializeMessageButtons()
    {
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
        for(Button button:messageButtons)
        {
            button.setOnClickListener(new MessageOnClickListener());
        }
    }
    private class MessageOnClickListener implements View.OnClickListener {
        public void onClick(View v)
        {


        }
    }
}
