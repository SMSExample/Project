package com.example.guy.smsclassproject;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
/**
 * Created by Isaac on 11/5/2015.
 */
public class HistoryActivityTest
        extends ActivityInstrumentationTestCase2 <HistoryActivity> {

    private HistoryActivity tester;
    private Button[] messageButtons;
    private Button searchButton;
    private Button prevButton;
    private Button nextButton;
    private EditText searchBar;
    private TextView pagenumber;
    private DraftsDatabase testdraftDatabase;
    private MessageObject messagesearch1;
    private MessageObject numbersearch;
    ArrayList<MessageObject> messagesToBeDisplayed;

    public HistoryActivityTest() {
        super(HistoryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        tester = new HistoryActivity();
        testdraftDatabase = new DraftsDatabase();
        messagesearch1 = new MessageObject("hey","5556",null, true);
        testdraftDatabase.addMessage(messagesearch1);
        numbersearch = new MessageObject("Meet me there","5554",null,false);
        testdraftDatabase.addMessage(numbersearch);

        messagesToBeDisplayed = testdraftDatabase.getAllTexts();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchBar = (EditText) tester.findViewById(R.id.searchKeyWord);
        searchButton = (Button) tester.findViewById(R.id.searchButton);
        //prevButton = (Button) tester.findViewById(R.id.prevButton);
        //nextButton = (Button) tester.findViewById(R.id.nextButton);
    }

    @SmallTest
    @UiThreadTest
    public void testButtons(){
        // Verifying the button exists.
        assertNotNull(tester.searchButton);
        assertNotNull(tester.prevButton);
        assertNotNull(tester.nextButton);
    }
    @SmallTest
    @UiThreadTest

    public void testSearchByMessageContent(){
        searchBar.setText("hey");
        searchButton.performClick();
        messagesToBeDisplayed = testdraftDatabase.getMessagesByKey(
                searchBar.getText().toString());
        assertEquals("hey",messagesearch1.getSmsMessage()); //Equal if text message has the word hey.
    }

}