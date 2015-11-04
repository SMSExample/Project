package com.example.guy.smsclassproject;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by txz141530 on 11/4/2015.
 */
public class DatabaseTest extends TestCase {

    private Database tester;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    ArrayList<MessageObject> messages;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        tester = new Database();

    }


    @Test
    public void testAddmessage()
    {
        messageObject1= new MessageObject("hi!", "5325554", true);
        messageObject2= new MessageObject("hi!", "5554", true);
        messageObject3= new MessageObject("hi!", "1115325554", true);
        tester.addMessage(messageObject1);
        messages = tester.getMessagesByNumber("5325554");
        assertEquals(messages.size(),3);
        assertEquals(messages.get(2),messageObject1);
        assertEquals(messages.get(1),messageObject2);
        assertEquals(messages.get(0),messageObject3);

    }

    @Test
    public void testAddmessage2()
    {

    }



}