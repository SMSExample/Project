package com.example.guy.smsclassproject;
import java.util.ArrayList;

/**
 * Created by Guy on 10/27/2015.
 */
public class MessageDatabase
{
    private static Database smsData;

    /**
     * creates a new message database
     */
    public MessageDatabase()
    {
        if(smsData==null)
            smsData = new Database();
    }

    /**
     * adds the specified message to the database
     * @param mO the message object
     */
    public void addMessage(MessageObject mO)
    {
        smsData.addMessage(mO);
    }

    /**
     * deletes the specified message from the database
     * @param mO the message object
     */
    public void deleteMessage(MessageObject mO)
    {
        smsData.deleteMessage(mO);
    }

    /**
     * deletes an entire array/list of messages
     * @param mOs the arrayList to be deleted
     */
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        smsData.deleteMessageList(mOs);
    }

    /**
     * searches an array list and returns all elements containing the key in
     * the message string.
     * @param key the word being searched for
     * @return a message object with the key anywhere in it
     */
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        return smsData.getMessagesByKey(key);
    }

    /**
     * searches array list and returns all elements containing the specified phone number
     * @param number the phone number to search for
     * @return a message object that has the phone number
     */
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {

        return smsData.getMessagesByNumber(number);
    }

    /**
     * gets all the messages in a list
     * @return message objects
     */
    public ArrayList<MessageObject> getAllTexts()
    {
        return smsData.getAllTexts();
    }

    /**
     * Erases all the data from the database.
     */
    public void clearData()
    {
        smsData.clearData();
    }
}
