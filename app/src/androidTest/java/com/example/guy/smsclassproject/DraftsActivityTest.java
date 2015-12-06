package com.example.guy.smsclassproject;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
/**
 * Created by ksl130230 on 11/5/2015.
 */
public class DraftsActivityTest extends ActivityInstrumentationTestCase2<DraftsActivity> {

    private DraftsActivity tester;
    private EditText searchText;
    private Button searchButton;
    private DraftsDatabase draftsDatabase;
    private MessageObject messageObject1;
    private MessageObject messageObject2;
    private MessageObject messageObject3;
    Button[] draftButtons;

    ArrayList<MessageObject> messagesToBeDisplayed;

    public DraftsActivityTest() {
        super(DraftsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        //DraftsActivity activityMonitor = getInstrumentation().addMonitor(NextActivity.class.getName(), null, false);
        draftsDatabase = new DraftsDatabase();
        messageObject1 = new MessageObject("hi", "5554",null, true);
        messageObject2 = new MessageObject("hi hi", "5555554",null, true);
        messageObject3 = new MessageObject("sup", "5435555554",null, true);
        draftsDatabase.addMessage(messageObject1);
        draftsDatabase.addMessage(messageObject2);
        draftsDatabase.addMessage(messageObject3);
        messagesToBeDisplayed = draftsDatabase.getAllTexts();
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchText = (EditText) tester.findViewById(R.id.searchText);
        searchButton = (Button) tester.findViewById(R.id.searchButton);


    }


    @SmallTest
    @UiThreadTest
    public void testSearch() {
        searchText.setText("hi");
        searchButton.performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Messages with the word hi", 2, messagesToBeDisplayed.size());

        searchText.setText("sup");
        searchButton.performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Messages with the word sup", 1, messagesToBeDisplayed.size());

        searchText.setText("yo");
        searchButton.performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Messages with the word yo", 0, messagesToBeDisplayed.size());

        searchText.setText("i");
        searchButton.performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Messages with the word i", 2, messagesToBeDisplayed.size());

    }

    @SmallTest
    @UiThreadTest
    public void testRedisplay()
    {
        assertNotNull(tester.draftButtons[0]);
        draftButtons[0].performClick();
        assertEquals("Size of the list after deletion is 2", 2, messagesToBeDisplayed.size()); //presses the first button, which deletes it from the drafts

        String buttonText0 = draftButtons[0].getText().toString();
        if(buttonText0.equals("5555554: hi hi"))
            assertSame("Text redisplayed on the first button", buttonText0, messageObject2); //gets the text of the current button, since the messageobject1 was in draftsButtons0 before, not it should have messageobject2

        assertNotNull(tester.draftButtons[1]);
        String buttonText1 = draftButtons[1].getText().toString();
        if(buttonText1.equals("5435555554: sup"))
            assertSame("Text redisplayed on the second button", buttonText1, messageObject3);

    }

    @SmallTest
    @UiThreadTest
    public void testMessageButtons()
    {

        assertNotNull(tester.draftButtons[1]);
        draftButtons[1].performClick();

        assertNotNull(tester.draftButtons[2]);
        draftButtons[2].performClick();

        assertEquals("The draftsDatabase now only contains 1 message", 1, messagesToBeDisplayed.size());

        assertNotNull(tester.draftButtons[0]);
        draftButtons[0].performClick();

        assertNull(draftsDatabase); //after you press all the buttons, the draftsDatabase should be empty because all the messages have been deleted

    }



}
