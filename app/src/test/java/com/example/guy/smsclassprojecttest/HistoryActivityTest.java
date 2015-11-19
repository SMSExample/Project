package com.example.guy.smsclassprojecttesttest;

import android.view.View;
import android.widget.Button;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static android.view.View.*;
import static org.junit.Assert.*;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import com.example.guy.smsclassproject.*;
/**
 * Created by gamalieljrz on 11/5/2015.
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

    @Before
    public void setUp() throws Exception {
        super.setUp();
        tester = new HistoryActivity();
        testdraftDatabase = new DraftsDatabase();
        messagesearch1 = new MessageObject("hey","5556", true);
        testdraftDatabase.addMessage(messagesearch1);
        numbersearch = new MessageObject("Meet me there","5554",false);
        testdraftDatabase.addMessage(numbersearch);

        messagesToBeDisplayed = testdraftDatabase.getAllTexts();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchBar = (EditText) tester.findViewById(R.id.searchKeyWord);
        searchButton = (Button) tester.findViewById(R.id.searchButton);
        //prevButton = (Button) tester.findViewById(R.id.prevButton);
        //nextButton = (Button) tester.findViewById(R.id.nextButton);
    }

    @Test
    public void testButtons(){
        // Verifying the button exists.
        assertNotNull(tester.searchButton);
        assertNotNull(tester.prevButton);
        assertNotNull(tester.nextButton);
    }
    @Test

    public void testSearchByMessageContent(){
        searchBar.setText("hey");
        searchButton.performClick();
        tester.messagesToBeDisplayed = testdraftDatabase.getMessagesByKey(
               searchBar.getText().toString());
       assertEquals("hey",messagesearch1.getSmsMessage()); //Equal if text message has the word hey.
    }

}