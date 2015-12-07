package com.example.guy.smsclassproject;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.BeforeClass;

import java.util.ArrayList;

/**
 * Created by Kenneth on 12/4/2015.
 */
public class ConversationActivityTest extends ActivityInstrumentationTestCase2 <ConversationActivity> {

    private ConversationActivity tester;
    private MessageDatabase testmessageDatabase;
    private TextView pageNumber;
    private Button prevButton;
    private Button nextButton;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    Button[] correspondentButtons;

    ArrayList<MessageObject> messagesToBeDisplayed;

    public ConversationActivityTest() {
        super(ConversationActivity.class);
    }

    @BeforeClass
    public void setUp() throws Exception {
        //super.setUp();

        testmessageDatabase = new MessageDatabase();
        messageObject1 = new MessageObject("hi", "5554",null, true);
        messageObject2 = new MessageObject("what's up", "5554",null, true);
        messageObject3 = new MessageObject("sup", "5435555554",null, true);
        testmessageDatabase.addMessage(messageObject1);
        testmessageDatabase.addMessage(messageObject2);
        testmessageDatabase.addMessage(messageObject3);
        messagesToBeDisplayed = testmessageDatabase.getAllTexts();
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        prevButton = (Button) tester.findViewById(R.id.prevButton);
        nextButton = (Button) tester.findViewById(R.id.nextButton);
    }

    @SmallTest
    @UiThreadTest
    public void testShouldAddMessage() { //testing if the conversations are actually created correctly

        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Number of conversations", 2, messagesToBeDisplayed.size());

        assertNotNull(tester.correspondentButtons[0]);
        String buttonText0 = correspondentButtons[0].getText().toString();
        if(buttonText0.equals("5554: what's up"))
            assertSame("Conversation with 5554 grouped successfully", buttonText0, messageObject1.toString());

        assertNotNull(tester.correspondentButtons[1]);
        String buttonText1 = correspondentButtons[1].getText().toString();
        if(buttonText1.equals("5435555554: sup"))
            assertSame("Conversation with 5435555554 grouped successfully", buttonText1, messageObject3.toString());
    }
}
