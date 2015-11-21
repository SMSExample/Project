package com.example.guy.smsclassproject;
import java.util.ArrayList;

/**
 * Created Chantel on 10/29/2015.
 */
public class DraftsDatabase
{
    private static Database draftData;

    /**
     * creates a new draft database
     */
    public DraftsDatabase()
    {
        if(draftData==null)
            draftData = new Database();
    }

    /**
     * adds the specified message to the database
     * @param mO the message object
     */
    public void addMessage(MessageObject mO)
    {
        draftData.addMessage(mO);
    }

    /**
     * deletes the specified message from the database
     * @param mO the message object
     */
    public void deleteMessage(MessageObject mO)
    {
        draftData.deleteMessage(mO);
    }

    /**
     * deletes an entire array/list of messages
     * @param mOs the arrayList to be deleted
     */
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        draftData.deleteMessageList(mOs);
    }

    /**
     * searches an array list and returns all elements containing the key in
     * the message string.
     * @param key the word being searched for
     * @return a message object with the key anywhere in it
     */
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        return draftData.getMessagesByKey(key);
    }

    /**
     * searches array list and returns all elements containing the specified phone number
     * @param number the phone number to search for
     * @return a message object that has the phone number
     */
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {
        return draftData.getMessagesByNumber(number);
    }

    /**
     * gets all the messages in a list
     * @return message objects
     */
    public ArrayList<MessageObject> getAllTexts()
    {
        return draftData.getAllTexts();
    }

    /**
     * Erases all the data from the database.
     */
    public void clearData()
    {
        draftData.clearData();
    }
}
