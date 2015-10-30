package com.example.guy.smsclassproject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

    //All values used on this page
    Button sendButton;
    Button contactButton;
    MessageDatabase messageDatabase;
    EditText numberText;
    EditText messageText;
    static String currentNumber;
    Button[] messageButtons;
    int page;
    final int RQS_PICKCONTACT = 1;
    ArrayList<MessageObject> messagesFromReceiver;
    IntentFilter intentFilter;
    boolean wasCreated;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texting);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        messageDatabase = new MessageDatabase();
        sendButton = (Button)findViewById(R.id.sendButton);
        contactButton = (Button) findViewById(R.id.contacts);
        numberText = (EditText)findViewById(R.id.numberText);
        messageText = (EditText) findViewById(R.id.messageText);
        initializeMessageButtons();
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
            }

        });

        //Action Listener for contact button
        contactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start activity to get contact
                final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
                Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
                startActivityForResult(intentPickContact, RQS_PICKCONTACT);
            }

        });
        wasCreated = true;


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
        messageDatabase.addMessage(new MessageObject(message, number, true));

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
        System.out.println(currentNumber);
        redisplayTexts();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode == RESULT_OK){
            if(requestCode == RQS_PICKCONTACT){
                Uri returnUri = data.getData();
                Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

                if(cursor.moveToNext()){
                    int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactID = cursor.getString(columnIndex_ID);

                    int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                    String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                    if(stringHasPhoneNumber.equalsIgnoreCase("1")){
                        Cursor cursorNum = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                                null,
                                null);

                        //Get the first phone number
                        if(cursorNum.moveToNext()){
                            int columnIndex_number = cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String stringNumber = cursorNum.getString(columnIndex_number);
                            numberText.setText(stringNumber);
                        }

                    }else{
                        numberText.setText("NO Phone Number");
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "NO data!", Toast.LENGTH_LONG).show();
                }
            }
        }
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
        messageButtons[0] = (Button)findViewById(R.id.textButton1);
        messageButtons[1] = (Button)findViewById(R.id.textButton2);
        messageButtons[2] = (Button)findViewById(R.id.textButton3);
        messageButtons[3] = (Button)findViewById(R.id.textButton4);
        messageButtons[4] = (Button)findViewById(R.id.textButton5);
        messageButtons[5] = (Button)findViewById(R.id.textButton6);
        messageButtons[6] = (Button)findViewById(R.id.textButton7);
        messageButtons[7] = (Button)findViewById(R.id.textButton8);
        messageButtons[8] = (Button)findViewById(R.id.textButton9);
        messageButtons[9] = (Button)findViewById(R.id.textButton10);
    }
    protected void onResume()
    {
        if(currentNumber!=null) {
            redisplayTexts();
            numberText.setText(currentNumber);
        }
        registerReceiver(intentReceiver,intentFilter);

        super.onResume();
    }
    protected void onPause()
    {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}
