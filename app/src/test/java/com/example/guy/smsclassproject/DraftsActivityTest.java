package com.example.guy.smsclassproject;

import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.view.*;
import android.widget.*;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        draftsDatabase = new DraftsDatabase();
        messageObject1 = new MessageObject("hi","5554",true);
        messageObject2 = new MessageObject("hi hi","5555554",true);
        messageObject3 = new MessageObject("sup","5435555554",true);
        draftsDatabase.addMessage(messageObject1);
        draftsDatabase.addMessage(messageObject2);
        draftsDatabase.addMessage(messageObject3);
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchText = (EditText) tester.findViewById(R.id.searchText);
        searchButton = (Button) tester.findViewById(R.id.searchButton);
    }
    @Test
    public void testSearchButton()
    {
        searchText.setText("hi");
        searchButton.performClick();
        assertEquals("Messages with the word hi",2,messagesToBeDisplayed.size());

    }


}