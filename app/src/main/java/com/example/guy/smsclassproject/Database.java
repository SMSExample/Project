
package com.example.guy.smsclassproject;
import java.util.ArrayList;
import java.util.HashMap;
public class Database
{
    private HashMap<String,ArrayList<MessageObject>> messages;
    private ArrayList<MessageObject> messagesInOrder;

    public Database()
    {
        clearData();
    }

    /**
     * adds a message object to the database array list
     * @param mO a message object
     */
    public void addMessage(MessageObject mO)
    {
        String num = mO.getNumber();
        if(!messages.containsKey(num))
        {
            messages.put(num,this.getExistingNumber(num));
        }
            messages.get(num).add(0,mO);
        messagesInOrder.add(0,mO);
    }

    /**
     *Delete the message object from the database
     * @param mO the message object
     */
    public void deleteMessage(MessageObject mO)
    {
        String num = mO.getNumber();
        ArrayList<MessageObject> messagesFromNum = messages.get(num);
        messagesFromNum.remove(mO);
        messagesInOrder.remove(mO);
    }

    /**
     *Delete all the messages in the given list.
     * @param mOs The list
     */
    public void deleteMessageList(ArrayList<MessageObject> mOs)
    {
        for(MessageObject mO: mOs)
        {
            deleteMessage(mO);
        }
    }

    /**
     *Take some key words and return all the messages that
     * contain those.
     * @param key The word(s).
     * @return Messages that contain the keyword(s).
     */
    public ArrayList<MessageObject> getMessagesByKey(String key)
    {
        ArrayList<MessageObject> messagesThatContainKey = new ArrayList<>();
        for(MessageObject message : messagesInOrder)
        {
            if((message.getSmsMessage().toLowerCase()).indexOf(key.toLowerCase())!=-1)
                messagesThatContainKey.add(message);
        }
        return messagesThatContainKey;
    }

    /**
     * Get all the text messages that were sent to
     * and from a phone number.
     * @param number The number that all the messages were sent
     * to and from.
     * @return All the messages that were sent to and from that number.
     */
    public ArrayList<MessageObject> getMessagesByNumber(String number)
    {
        return messages.get(number);
    }

    /**
     * Checks to see if a number already
     * exists in the database.
     * @param newNum The number that is checked.
     * @return An arraylist of all the texts sent
     * to and from the checked number if it already existed.
     */
    public ArrayList<MessageObject> getExistingNumber(String newNum)
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

    /**
     * Get all the texts in the order that they
     * were produced
     * @return The Arraylist of all the messages that
     * were produced in the order that they were produced.
     */
    public ArrayList<MessageObject> getAllTexts()
    {
        return messagesInOrder;
    }

    /**
     * Erases all the data from the database.
     */
    public void clearData()
    {
        messages = new HashMap<>();
        messagesInOrder = new ArrayList<>();
    }
}
