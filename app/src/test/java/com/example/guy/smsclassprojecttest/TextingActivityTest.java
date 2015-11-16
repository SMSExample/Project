package com.example.guy.smsclassprojecttest;

import android.widget.Button;
import android.widget.EditText;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import com.example.guy.smsclassproject.*;
/**
 * Created by cxb120230 on 11/5/2015.
 */
public class TextingActivityTest extends TestCase
{
    EditText numText;
    EditText messageText;
    Button send;

    private TextingActivity tester;
    private MessageDatabase messageDatabase;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    ArrayList<MessageObject> messagesToBeDisplayed;

    public void setUp() throws Exception {
        super.setUp();

        messageDatabase = new MessageDatabase();
        messagesToBeDisplayed = messageDatabase.getAllTexts();
        tester = new TextingActivity();//getActivity();
        numText = (EditText) tester.findViewById(R.id.numberText);
        messageText = (EditText) tester.findViewById(R.id.messageText);
        send = (Button) tester.findViewById(R.id.sendButton);

    }

    @Test
    public void testSend()
    {

    }

}