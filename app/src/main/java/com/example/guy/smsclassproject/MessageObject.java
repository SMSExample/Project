package com.example.guy.smsclassproject;

/**
 * Created by Guy on 10/26/2015.
 */
public class MessageObject
{
    private String smsMessage;
    private String num;
    private boolean sentSMS;

    /**
     * creates default message object
     */
    public MessageObject(){};

    /**
     * creates message object based on inputs
     * @param message the message that was sent
     * @param number phone number
     * @param sentByUser boolean value that marks if message was sent by the phone
     */
    public MessageObject(String message, String number, boolean sentByUser)
    {
        smsMessage = message;
        num = number;
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
    public boolean wasSentByUser()
    {
        return sentSMS;
    }

}
