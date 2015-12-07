package com.example.guy.smsclassproject;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by gamalieljrz on 12/4/2015.
 */
public class DraftsDatabaseTest  {


    private DraftsDatabase tester;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    ArrayList<MessageObject> listOfMessages;
    int databaseSize;

    @Before
    public void setUp() {
        tester = new DraftsDatabase();
        tester.clearData();
        messageObject1 = new MessageObject("test", "5554", null, true);
        messageObject2 = new MessageObject("test2", "5556", null, false);
        messageObject3 = new MessageObject("test21", "5554", null, false);

    }

    @Test
    public void testAddMessage(){

        databaseSize = 2;
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        listOfMessages = tester.getAllTexts();
        assertEquals("Testing Add Message: ",databaseSize, listOfMessages.size()); //Will check if message was added to database.
    }
    @Test
    public void testDeleteMessage(){

        databaseSize = 1;
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        tester.deleteMessage(messageObject1);
        listOfMessages = tester.getAllTexts();
        assertEquals("Testing Delete Message: ",databaseSize, listOfMessages.size());//Tests delete method works.
    }

    @Test
    public void testDeleteMessageList()
    {
        databaseSize = 0;
        ArrayList<MessageObject> messageList = new ArrayList<>();
        messageList.add(messageObject1);
        messageList.add(messageObject2);
        messageList.add(messageObject3);
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        tester.addMessage(messageObject3);
        tester.deleteMessageList(messageList);
        listOfMessages = tester.getAllTexts();
        assertEquals("Testing Delete Message List: ",databaseSize, listOfMessages.size());//Tests delete list method works.
    }

    @Test
    public void testGetMessageByKey()
    {
        int listSize = 2;
        String key = "test2";
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        tester.addMessage(messageObject3);
        ArrayList<MessageObject> messageList = tester.getMessagesByKey(key);
        assertEquals("Testing Get Message By Key: ",listSize,messageList.size());// Tests get message by key.
    }

    @Test
    public void testGetMessageByNumber()
    {
        int listSize = 2;
        String number = "5554";
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        tester.addMessage(messageObject3);
        ArrayList<MessageObject> messageList = tester.getMessagesByNumber(number);
        assertEquals("Testing Get Message By Number: ",listSize,messageList.size());//Tests get message by number.
    }

    @Test
    public void testGetAllTexts()
    {
        int listSize = 3;
        tester.addMessage(messageObject1);
        tester.addMessage(messageObject2);
        tester.addMessage(messageObject3);
        ArrayList<MessageObject> messageList = tester.getAllTexts();
        assertEquals("Testing Get All Texts: ",listSize,messageList.size());//Tests get all texts.
    }
}
