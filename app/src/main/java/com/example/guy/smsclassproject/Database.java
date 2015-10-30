
package com.example.guy.smsclassproject;
import android.os.Message;
import com.example.guy.smsclassproject.MessageObject;

import java.util.ArrayList;
import java.util.HashMap;
public class Database
{
    private HashMap<String,ArrayList<MessageObject>> messages;
    public Database()
    {
        messages = new HashMap<>();
    }
    public void addMessage(MessageObject mO)
    {
        String num = mO.getNumber();
        if(!messages.containsKey(num))
        {
            messages.put(num,this.GetExistingNumber(num));
        }
            messages.get(num).add(mO);
    }
    public void deleteMessage(MessageObject mO)
    {
        String num = mO.getNumber();
        ArrayList<MessageObject> messagesFromNum = messages.get(num);
        for(int x = 0; x<messagesFromNum.size(); x++)
        {
            MessageObject messageObj = messagesFromNum.get(0);
            if(messageObj==mO)
            {
                messagesFromNum.remove(x);
                break;
            }
        }
    }
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        for(MessageObject mO: mOs)
        {
            deleteMessage(mO);
        }
    }
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        ArrayList<MessageObject> messagesThatContainKey = new ArrayList<>();
        for(String number : messages.keySet())
        {
            ArrayList<MessageObject> messagesByNumber = messages.get(number);
            for(MessageObject message : messagesByNumber)
            {
                if(message.getSmsMessage().indexOf(key)!=-1)
                    messagesThatContainKey.add(message);
            }
        }
        return messagesThatContainKey;
    }
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {
        return messages.get(number);
    }
    public ArrayList<MessageObject> GetExistingNumber(String newNum)
    {
        for(String oldNum : messages.keySet())
        {
            if(newNum.length()<oldNum.length())
            {

                String shortenedVersion = oldNum.substring(oldNum.length()-newNum.length());
                if(shortenedVersion.equals(newNum))
                    return messages.get(oldNum);
            }
            else
            {
                String shortenedVersion = newNum.substring(newNum.length()-oldNum.length());
                if(shortenedVersion.equals(oldNum))
                    return messages.get(oldNum);
            }
        }
        return new ArrayList<MessageObject>();
    }

}
