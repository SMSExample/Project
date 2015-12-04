package com.example.guy.smsclassproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.guy.smsclassproject.MessageObject;

public class SmsReceiver extends BroadcastReceiver
{
    MessageDatabase messageDatabase;
    //Whenever the phone receives a text message, the onRecieved method is called.
    public void onReceive(Context context, Intent intent)
    {
        //The message is retrieved from the intent object.
        Bundle bundle = intent.getExtras();
        SmsMessage message = null;
        String textAndNum = "";
        String sender = null;
        String stringMessage = null;
        if(bundle!=null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for(int i= 0;i<pdus.length;i++)
            {
                message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender = message.getOriginatingAddress();
                if(sender.length()>0)
                {
                    stringMessage = message.getMessageBody();
                    textAndNum+=TextingActivity.formatedNumber(sender)+": "+stringMessage+"\n";
                    break;
                }
            }
            if(messageDatabase==null)
                messageDatabase = new MessageDatabase();
            messageDatabase.addMessage(new MessageObject(stringMessage, sender, null, false));


            //A toast is displayed. A toast is just a message on a screen.
            //It has no further functionality. Deleting this line doesn't
            //make a big difference.

            Toast.makeText(context,textAndNum, Toast.LENGTH_SHORT).show();
            //An intent is thrown that contains the SMS message. This
            //message is caught by the TextingActivity's Braudcast receiver.
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms",sender);
            context.sendBroadcast(broadcastIntent);
        }
    }
}