package com.example.guy.smsclassproject;

/**
 * Created by Guy on 10/26/2015.
 */
public class MessageObject
{
    private String smsMessage;
    private String num;
    private boolean sentSMS;
    public MessageObject(){};
    public MessageObject(String message, String number, boolean sentByUser)
    {
        smsMessage = message;
        num = number;
        sentSMS = sentByUser;
    }
    public String getSmsMessage()
    {
        return smsMessage;
    }
    public String getNumber()
    {
        return num;
    }
    public boolean wasSentByUser()
    {
        return sentSMS;
    }

}
