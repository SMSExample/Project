package com.example.guy.smsclassproject;

/**
 * Created by Guy on 10/26/2015.
 */
public class MessageObject
{
    private String smsMessage;
    private String num;
    private boolean sentSMS;
    public MessageObject(String message, String number, boolean sentByUser)
    {

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
