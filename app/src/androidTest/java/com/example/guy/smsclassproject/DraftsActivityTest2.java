package com.example.guy.smsclassproject;

import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Guy on 11/16/2015.
 */
public class DraftsActivityTest2 extends ActivityInstrumentationTestCase2<DraftsActivity>
{
    private EditText searchText;
    private Button searchButton;
    private DraftsDatabase draftsDatabase;
    ArrayList<MessageObject> messagesToBeDisplayed;
    DraftsActivity tester;

    public DraftsActivityTest2()
    {
        super(DraftsActivity.class);
    }

    @Override
    @UiThreadTest
    public void setUp() throws Exception
    {
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        draftsDatabase = new DraftsDatabase();
        draftsDatabase.clearData();
        MessageObject messageObject1 = new MessageObject("hi", "5554",null, true);
        MessageObject messageObject2 = new MessageObject("hi hi", "5555554",null, true);
        MessageObject messageObject3 = new MessageObject("sup", "5435555554",null, true);
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
        assertEquals("Messages with the word sup", 2, messagesToBeDisplayed.size());

    }
}
