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
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        SmsMessage message = null;
        String text = "";
        if(bundle!=null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for(int i= 0;i<pdus.length;i++)
            {
                message = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String sender = message.getOriginatingAddress();
                if(sender.length()>0)
                {
                    text+=sender+": "+message.getMessageBody()+"\n";
                    break;
                }
            }
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms",text);
            context.sendBroadcast(broadcastIntent);
        }
    }
}