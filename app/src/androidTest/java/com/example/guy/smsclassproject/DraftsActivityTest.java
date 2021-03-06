package com.example.guy.smsclassproject;

import android.os.Looper;
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
    @UiThreadTest
    public void setUp() throws Exception {
        super.setUp();
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        draftsDatabase = new DraftsDatabase();
        draftsDatabase.clearData();
        messageObject1 = new MessageObject("hi", "5554",null, true);
        messageObject2 = new MessageObject("hi hi", "5555554",null, true);
        messageObject3 = new MessageObject("sup", "5435555554",null, true);
        draftsDatabase.addMessage(messageObject1);
        draftsDatabase.addMessage(messageObject2);
        draftsDatabase.addMessage(messageObject3);
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
        tester.draftButtons[0].performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("Size of the list after deletion is 2", 2, messagesToBeDisplayed.size()); //presses the first button, which deletes it from the drafts

        String buttonText0 = tester.draftButtons[0].getText().toString();
        assertEquals("Test redisplay on the first button","555-5554: hi hi",buttonText0);


    }

    @SmallTest
    @UiThreadTest
    public void testMessageButtons()
    {
        assertNotNull(tester.draftButtons[0]);
        tester.draftButtons[0].performClick();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals("The draftsDatabase now only contains 1 message", 2, messagesToBeDisplayed.size());

    }



}
