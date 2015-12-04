package com.example.guy.smsclassproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by Guy on 10/26/2015.
 */
public class MessageObject extends Activity
{
    private String smsMessage;
    private String num;
    private String name;
    private boolean sentSMS;

    /**
     * creates default message object
     */
    public MessageObject(){};

    /**
     * creates message object based on inputs
     * @param message the message that was sent
     * @param number phone number
     * @param nameOfContact name of contact
     * @param sentByUser boolean value that marks if message was sent by the phone
     */
    public MessageObject(String message, String number, String nameOfContact, boolean sentByUser)
    {
        smsMessage = message;
        num = number;
        name = nameOfContact;
        sentSMS = sentByUser;
    }

    /**
     * gets message from current object
     * @return the message
     */
    public String getSmsMessage()
    {
        return smsMessage;
    }

    /**
     * gets phone number from current object
     * @return the phone number
     */
    public String getNumber()
    {
        return num;
    }

    /**
     * gets if it was sent by user from current object
     * @return true or false
     */

    public String getNameOfContact() {
        return name;
    }
    /*
 * Returns contact's id
 */
    public String getContactName(String phoneNumber, Context context) {
        ContentResolver mResolver = context.getContentResolver();

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));

        Cursor cursor = mResolver.query(uri, new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID }, null, null, null);

        String contactId = "";

        if (cursor.moveToFirst()) {
            do {
                contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            } while (cursor.moveToNext());
        }

        cursor.close();
        cursor = null;
        return contactId;
    }

    /**
     * gets name of message object
     * @return
     */
    public boolean wasSentByUser()
    {
        return sentSMS;
    }

}
