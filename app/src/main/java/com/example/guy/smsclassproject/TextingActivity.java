package com.example.guy.smsclassproject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.*;
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
import android.provider.ContactsContract.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class TextingActivity extends AppCompatActivity {

    //All values used on this page
    Button sendButton;
    Button saveButton;
    Button nextPageButton;
    Button prevPageButton;
    Button contactButton;
    Button addContactButton;
    MessageDatabase messageDatabase;
    DraftsDatabase draftsDatabase;
    EditText numberText;
    EditText messageText;
    static String currentNumber;
    Button[] messageButtons;
    int page;
    final int RQS_PICKCONTACT = 1;
    ArrayList<MessageObject> messagesFromReceiver;
    IntentFilter intentFilter;
    Intent addContact; //used to open contacts and add contact
    boolean wasCreated;
    static MessageObject continueMessage;
    private TextView pageNumber;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        String number;

        /**
         * acts when the phone recives a text
         * @param context android class
         * @param intent android class
         */
        public void onReceive(Context context, Intent intent) {
            String textMessage = intent.getExtras().getString("sms");
            String message = textMessage.substring(textMessage.indexOf(':') + 2);
            number = textMessage.substring(0, textMessage.indexOf(':'));
            messageDatabase.addMessage(new MessageObject(message, number, null, false));

            if (isSameNumber())
                redisplayTexts();
        }

        /**
         *  checks shorted number and full number and determines if they are the same
         * @return last four digits of a phone number
         */
        private boolean isSameNumber() {
            if (currentNumber == null)
                return false;
            String shortenedVersion = number.substring(number.length() - currentNumber.length());
            return shortenedVersion.equals(currentNumber);
        }
    };

    /**
     * actions done when the activity starts
     *
     * @param savedInstanceState android class
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texting);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        messageDatabase = new MessageDatabase();
        draftsDatabase = new DraftsDatabase();
        sendButton = (Button) findViewById(R.id.sendButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        contactButton = (Button) findViewById(R.id.contacts);
        addContactButton = (Button) findViewById(R.id.add);
        nextPageButton = (Button) findViewById(R.id.nextButton);
        prevPageButton = (Button) findViewById(R.id.prevButton);
        numberText = (EditText) findViewById(R.id.numberText);
        messageText = (EditText) findViewById(R.id.messageText);
        pageNumber = (TextView) findViewById(R.id.pageNumber);
        initializeMessageButtons();
        sendButton.setOnClickListener(new MessageOnClickListener());

        //Action Listener for contact button
        contactButton.setOnClickListener(new MessageOnClickListener());

        //Action for add button
        addContactButton.setOnClickListener(new MessageOnClickListener());
        nextPageButton.setOnClickListener(new MessageOnClickListener());
        prevPageButton.setOnClickListener(new MessageOnClickListener());
        saveButton.setOnClickListener(new MessageOnClickListener());

        wasCreated = true;
        if (continueMessage != null) {
            numberText.setText(continueMessage.getNumber());
            messageText.setText(continueMessage.getSmsMessage());
        }
        continueMessage = null;


    }

    /**
     * Checks what button is clicked and responds accordingly
     */
    private class MessageOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v == sendButton) {
                sendMessage();
            } else if (v == contactButton) {
                final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
                Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
                startActivityForResult(intentPickContact, RQS_PICKCONTACT);
            } else if (v == addContactButton) {
                //intent opens add contact screen in contacts app
                addContact = new Intent(Intent.ACTION_INSERT);
                addContact.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivity(addContact);
            } else if (v == nextPageButton) {
                if ((messagesFromReceiver.size() - 1) / 10 <= page)
                    return;
                page++;
                redisplayTexts();
            } else if (v == prevPageButton) {
                if (page <= 0)
                    return;
                page--;
                redisplayTexts();
            } else if (v == saveButton) {
                saveToDrafts();
                Toast.makeText(getBaseContext(), "Message saved!", Toast.LENGTH_SHORT).show();
            } else {
                Button button = (Button) v;
                int textIndex = getButtonIndex(button) + 10 * page;
                if (textIndex >= messagesFromReceiver.size())
                    return;
                MessageObject messageInButton = messagesFromReceiver.get(textIndex);
                SingleTextActivity.setMessage(messageInButton);
                startActivity(new Intent(TextingActivity.this, SingleTextActivity.class));
            }
        }
    }

    public static void continueMessage(MessageObject mO) {
        continueMessage = mO;
    }

    /**
     * creates message object for database, determines if message is sent,
     * and stores the current number to make sure messages display correctly
     */
    private void sendMessage() {
        String number = fixNumber(numberText.getText().toString());
        if (number.equals("")) {
            Toast.makeText(getBaseContext(), "Please enter a number.", Toast.LENGTH_LONG).show();
            return;
        }
        String message = messageText.getText().toString();
        messageDatabase.addMessage(new MessageObject(message, number, null, true));

        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";

        PendingIntent sendPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(TextingActivity.this, "SMS sent", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

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

    /**
     * makes sure phone number is only composed of numbers
     *
     * @param oldString phone number
     * @return phone number with only numbers
     */
    private String fixNumber(String oldString) {
        String newString = "";
        for (char c : oldString.toCharArray()) {
            if (((int) c) <= 57 && ((int) c) >= 48)
                newString += c;
        }
        return newString;
    }

    /**
     * allows program to get contacts from phone
     *
     * @param requestCode query request number
     * @param resultCode  number that corresponds with result
     * @param data        intent that helps get correct data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (requestCode == RQS_PICKCONTACT) {
                Uri returnUri = data.getData();
                Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

                if (cursor.moveToNext()) {
                    int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactID = cursor.getString(columnIndex_ID);

                    int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                    String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                    if (stringHasPhoneNumber.equalsIgnoreCase("1")) {
                        Cursor cursorNum = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                                null,
                                null);

                        //Get the first phone number
                        if (cursorNum.moveToNext()) {
                            int columnIndex_number = cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String stringNumber = cursorNum.getString(columnIndex_number);
                            numberText.setText(stringNumber);
                        }

                    } else {
                        numberText.setText("NO Phone Number");
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "NO data!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * displays recent texts from/to the current number on screen
     */
    private void redisplayTexts() {
        messagesFromReceiver = messageDatabase.getMessagesByNumber(currentNumber);
        int amountOfMessages = messagesFromReceiver.size();
        for (int i = 0; i < 10; i++) {
            if (i + 10 * page >= amountOfMessages) {
                clearTheRest(i);
                break;
            }
            MessageObject message = messagesFromReceiver.get(i + 10 * page);
            String str;
            if (message.wasSentByUser()) {
                str = "You: ";
            }
            else if (contactExists(getBaseContext(),message.getNumber())){
                str = message.getContactName(message.getNumber(),
                        getBaseContext()) + ": "; //name of contact, Need to Modify!!!
            }
            else
                str = message.getNumber() + ": ";
            str += message.getSmsMessage();
            messageButtons[i].setText(str);

        }
        pageNumber.setText("" + (page + 1));
    }

    /**
     * clears text from buttons, mostly used when going to a new
     * page of results
     *
     * @param startButton button to start clearing from
     */
    private void clearTheRest(int startButton) {
        for (int i = startButton; i < 10; i++) {
            messageButtons[i].setText("");
        }
    }

    /**
     * sets up all the buttons used for messages
     */
    private void initializeMessageButtons() {
        messageButtons = new Button[10];
        messageButtons[0] = (Button) findViewById(R.id.textButton1);
        messageButtons[1] = (Button) findViewById(R.id.textButton2);
        messageButtons[2] = (Button) findViewById(R.id.textButton3);
        messageButtons[3] = (Button) findViewById(R.id.textButton4);
        messageButtons[4] = (Button) findViewById(R.id.textButton5);
        messageButtons[5] = (Button) findViewById(R.id.textButton6);
        messageButtons[6] = (Button) findViewById(R.id.textButton7);
        messageButtons[7] = (Button) findViewById(R.id.textButton8);
        messageButtons[8] = (Button) findViewById(R.id.textButton9);
        messageButtons[9] = (Button) findViewById(R.id.textButton10);
        for (Button button : messageButtons) {
            button.setOnClickListener(new MessageOnClickListener());
        }
    }

    /**
     * finds where button is in the list
     *
     * @param b a button
     * @return an int 0-9 if foun, -1 if not found
     */
    private int getButtonIndex(Button b) {
        for (int x = 0; x < messageButtons.length; x++) {
            if (messageButtons[x] == b)
                return x;
        }
        return -1;
    }

    /**
     * actions done when the app is reopened
     */
    protected void onResume() {
        if (currentNumber != null) {
            redisplayTexts();
            numberText.setText(currentNumber);
        }
        registerReceiver(intentReceiver, intentFilter);

        super.onResume();
    }

    /**
     * actions done when app is paused
     */
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

    /**
     * stores message in draft database
     */
    private void saveToDrafts() {
        String number = numberText.getText().toString();
        String message = messageText.getText().toString();
        draftsDatabase.addMessage(new MessageObject(message, number, null, true));
        Toast.makeText(getApplicationContext(), "Message saved to drafts.", Toast.LENGTH_LONG).show();
    }

    /**
     * method checks if contact exists on your phone
     */
    public boolean contactExists(Context context, String number) {
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] myPhoneNumberProjection = {
                PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME
        };
        Cursor mycursor = context.getContentResolver().query(
                uri, myPhoneNumberProjection, null, null, null);
        if (mycursor != null && mycursor.moveToFirst()) {
            return true;// contact exists
        }
        return false;
    }

}
