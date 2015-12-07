package com.example.guy.smsclassproject;

import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;

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
    private String number1;
    private String number2;
    Button[] correspondentButtons;

    ArrayList<MessageObject> messagesToBeDisplayed;

    public ConversationActivityTest() {
        super(ConversationActivity.class);
    }

    @Override
    @UiThreadTest
    public void setUp() throws Exception {
        //super.setUp();
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        number1 = "5554";
        number2 = "5435555554";
        testmessageDatabase = new MessageDatabase();
        messageObject1 = new MessageObject("hi",number1 ,null, true);
        messageObject2 = new MessageObject("what's up", number1,null, true);
        messageObject3 = new MessageObject("sup",number2 ,null, true);
        testmessageDatabase.addMessage(messageObject1);
        testmessageDatabase.addMessage(messageObject2);
        testmessageDatabase.addMessage(messageObject3);
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        prevButton = (Button) tester.findViewById(R.id.prevButton);
        nextButton = (Button) tester.findViewById(R.id.nextButton);

    }

    @SmallTest
    @UiThreadTest
    public void testIfSameNumber()
    {
        assertTrue(number1+" and "+number2+" are the same numbers",Database.isSameNumber(number1,number2));
        assertTrue(number2+" and "+number1+" are the same numbers",Database.isSameNumber(number2,number1));
    }

    @SmallTest
    @UiThreadTest
    public void testShouldAddMessage() { //testing if the conversations are actually created correctly

        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Number of conversations", 1, messagesToBeDisplayed.size());

        assertNotNull(tester.correspondentButtons[0]);
        String buttonText0 = tester.correspondentButtons[0].getText().toString();
        assertEquals("First button tested","("+543+")"+555+"-"+5554+": sup",buttonText0);


        assertNotNull(tester.correspondentButtons[1]);
        String buttonText1 = tester.correspondentButtons[1].getText().toString();
        assertEquals("First button tested","",buttonText1);
    }
}
