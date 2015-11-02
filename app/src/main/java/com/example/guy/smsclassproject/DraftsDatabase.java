package com.example.guy.smsclassproject;
import java.util.ArrayList;

/**
 * Created Chantel on 10/29/2015.
 */
public class DraftsDatabase
{
    private static Database draftData;

    public DraftsDatabase()
    {
        if(draftData==null)
            draftData = new Database();
    }
    public void addMessage(MessageObject mO)
    {
        draftData.addMessage(mO);
    }
    public void deleteMessage(MessageObject mO)
    {
        draftData.deleteMessage(mO);
    }
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        draftData.deleteMessageList(mOs);
    }
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        return draftData.getMessagesByKey(key);
    }
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {
        return draftData.getMessagesByNumber(number);
    }
    public ArrayList<MessageObject> getAllTexts()
    {
        return draftData.getAllTexts();
    }
}
