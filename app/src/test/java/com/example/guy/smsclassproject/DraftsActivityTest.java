package com.example.guy.smsclassproject;

import android.content.DialogInterface;
import android.provider.Telephony;
import android.test.ActivityInstrumentationTestCase2;
import android.view.*;
import android.widget.*;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import android.content.*;
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
    ArrayList<MessageObject> messagesToBeDisplayed;

    public DraftsActivityTest()
    {
        super(DraftsActivity.class);
    }
    @BeforeClass
    public void setUp() throws Exception
    {
        super.setUp();
       // DraftsActivity activityMonitor = getInstrumentation().addMonitor(NextActivity.class.getName(), null, false);

        draftsDatabase = new DraftsDatabase();
        messageObject1 = new MessageObject("hi","5554",true);
        messageObject2 = new MessageObject("hi hi","5555554",true);
        messageObject3 = new MessageObject("sup","5435555554",true);
        draftsDatabase.addMessage(messageObject1);
        draftsDatabase.addMessage(messageObject2);
        draftsDatabase.addMessage(messageObject3);
        messagesToBeDisplayed = draftsDatabase.getAllTexts();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), DraftsActivity.class);
        setActivityIntent(intent);
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchText = (EditText) tester.findViewById(R.id.searchText);
        searchButton = (Button) tester.findViewById(R.id.searchButton);

    }


    @Test
    public void testSearch()
    {
        searchText.setText("hi");
        searchButton.performClick();
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        assertEquals(100,messagesToBeDisplayed.size());

        searchText.setText("sup");
        searchButton.performClick();
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        assertEquals("Messages with the word sup",1,messagesToBeDisplayed.size());

        searchText.setText("yo");
        searchButton.performClick();
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        assertEquals("Messages with the word yo",0,messagesToBeDisplayed.size());

        searchText.setText("i");
        searchButton.performClick();
        messagesToBeDisplayed=draftsDatabase.getMessagesByKey(searchText.getText().toString());
        assertEquals("Messages with the word i",2,messagesToBeDisplayed.size());

    }


}