package com.example.guy.smsclassproject;
import java.util.ArrayList;

/**
 * Created by Guy on 10/27/2015.
 */
public class MessageDatabase
{
    private static Database smsData;

    public MessageDatabase()
    {
        if(smsData==null)
            smsData = new Database();
    }
    public void addMessage(MessageObject mO)
    {
        smsData.addMessage(mO);
    }
    public void deleteMessage(MessageObject mO)
    {
        smsData.deleteMessage(mO);
    }
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        smsData.deleteMessageList(mOs);
    }
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        return smsData.getMessagesByKey(key);
    }
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {
        return smsData.getMessagesByNumber(number);
    }
}
