package com.example.guy.smsclassproject;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.junit.*;

import java.util.ArrayList;

/**
 * Created by gamalieljrz on 12/4/2015.
 */
public class MessageObjectTest extends ActivityInstrumentationTestCase2<MessageObject> {

    public MessageObjectTest(){
        super(MessageObject.class);
    }
    private MessageObject tester;
    private MessageDatabase testDatabase;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    String name;
    String number;
    String message;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        testDatabase = new MessageDatabase();
        messageObject1 = new MessageObject(null, null, null,true);
        messageObject2 = new MessageObject(null, null, null, false);
        messageObject3 = new MessageObject(null, null, null, true);

    }

    @Test
    public void testCreateObject(){
        name = "John";
        number = "5554";
        message = "create this object";
        messageObject1 = new MessageObject("create this object","5554","John", true);
        assertEquals(name, messageObject1.getNameOfContact());
        assertEquals(number, messageObject1.getNumber());
        assertEquals(message, messageObject1.getSmsMessage());//Tests that the created message object is correct
    }

}
