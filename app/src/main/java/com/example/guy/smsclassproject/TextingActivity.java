package com.example.guy.smsclassproject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TextingActivity extends AppCompatActivity {

    Button sendButton;
    MessageDatabase messageDatabase;
    EditText numberText;
    EditText messageText;
    String currentNumber;
    Button[] messageButtons;
    int page;
    ArrayList<MessageObject> messagesFromReceiver;
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String textMessage = intent.getExtras().getString("sms");
            String message = textMessage.substring(textMessage.indexOf(':')+2);
            String number = textMessage.substring(0, textMessage.indexOf(':'));
            messageDatabase.addMessage(new MessageObject(message, number, false));

            if(number.equals(currentNumber))
            {
                redisplayTexts();
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        System.out.print("ERROR");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texting);
        messageDatabase = new MessageDatabase();
        sendButton = (Button)findViewById(R.id.sendButton);
        numberText = (EditText)findViewById(R.id.numberText);
        messageText = (EditText) findViewById(R.id.messageText);
        initializeMessageButtons();
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
            }
        });



    }
    private void sendMessage()
    {
        String number = numberText.getText().toString();
        if(number.equals(""))
        {
            Toast.makeText(getBaseContext(),"Please enter a number.", Toast.LENGTH_LONG).show();
            return;
        }
        String message = messageText.getText().toString();
        messageDatabase.addMessage(new MessageObject(message, number,true));

        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";

        PendingIntent sendPI = PendingIntent.getBroadcast(this,0,new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver()
        {
            public void onReceive(Context context, Intent intent)
            {
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(TextingActivity.this, "SMS sent", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),"Generic Failure", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(),"No Service", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        },new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "SMS Not Delivered", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(number, null, message, sendPI, deliveredPI);
        currentNumber = number;
        redisplayTexts();

    }
    private void redisplayTexts()
    {
        messagesFromReceiver = messageDatabase.getMessagesByNumber(currentNumber);
        int amountOfMessages = messagesFromReceiver.size();
        for(int i = 0; i<10; i++)
        {
            if(i+10*page>=amountOfMessages)
            {
                clearTheRest(i);
                break;
            }
            MessageObject message = messagesFromReceiver.get(i+10*page);
            String str;
            if(message.wasSentByUser())
                str = "You: ";
            else
                str = message.getNumber()+": ";
            str +=message.getSmsMessage();
            messageButtons[i].setText(str);
        }
    }
    private void clearTheRest(int startButton)
    {
        for(int i =  startButton; i<10; i++)
        {
            messageButtons[i].setText("");
        }
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
    }
    protected void onResume()
    {
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }
    protected void onPause()
    {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}
