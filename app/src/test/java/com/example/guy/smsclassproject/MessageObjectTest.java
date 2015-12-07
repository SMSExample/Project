package com.example.guy.smsclassproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by gamalieljrz on 12/4/2015.
 */
public class MessageObjectTest
{

    private MessageObject tester;

    @Before
    public void setUp()
    {
        tester = new MessageObject("Hi", "1234","Bob",true);
    }

    @Test
    public void testMessage() throws Exception
    {
        assertEquals("Testing the message", "Hi", tester.getSmsMessage());
    }

    @Test
    public void testNumber()
    {
        assertEquals("Testing the number", "1234", tester.getNumber());
    }

    @Test
    public void testName()
    {
        assertEquals("Test the name", "Bob", tester.getNameOfContact());
    }

    @Test
    public void testIfSentByUser()
    {
        assertEquals("Test if sent by user", true, tester.wasSentByUser());
    }
}
