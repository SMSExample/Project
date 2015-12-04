package com.example.guy.smsclassproject;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by gamalieljrz on 12/4/2015.
 */
public class MessageDatabaseTest extends ActivityInstrumentationTestCase2<MessageDatabase> {

    public MessageDatabaseTest() {super(MessageDatabase.class);}
    private MessageDatabase tester;
    private MessageDatabase testMessageDatabase;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    ArrayList<MessageObject> listOfMessages;
    int databaseSize;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        tester = getActivity();
        testMessageDatabase = new MessageDatabase();
        messageObject1 = new MessageObject("test", "5554", null, true);
        messageObject2 = new MessageObject("test2", "5556", null, false);
        messageObject3 = new MessageObject("test3", "5554", null, false);

    }

    @Test
    public void testAddMessage(){

        databaseSize = 2;
        testMessageDatabase.addMessage(messageObject1);
        testMessageDatabase.addMessage(messageObject2);
        listOfMessages = testMessageDatabase.getAllTexts();
        assertEquals(databaseSize, listOfMessages.size()); //Will check if message was added to database.
    }
    @Test
    public void testDeleteMessage(){

        databaseSize = 1;
        testMessageDatabase.addMessage(messageObject1);
        testMessageDatabase.addMessage(messageObject2);
        testMessageDatabase.deleteMessage(messageObject1);
        listOfMessages = testMessageDatabase.getAllTexts();
        assertEquals(databaseSize, listOfMessages.size());//Tests delete method works.
    }
}
