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
 * Created by gamalieljrz on 11/5/2015.
 */
public class HistoryActivityTest extends ActivityInstrumentationTestCase2 <HistoryActivity> {

    private HistoryActivity tester;
    private Button[] messageButtons;
    private Button searchButton;
    private Button prevButton;
    private Button nextButton;
    private EditText searchBar;
    private TextView pagenumber;
    private MessageDatabase testMessageDatabase;
    private MessageObject messagesearch1;
    private MessageObject numbersearch;
    ArrayList<MessageObject> messagesToBeDisplayed;

    public HistoryActivityTest() {
        super(HistoryActivity.class);
    }

    @BeforeClass
    public void setUp() throws Exception {
        //super.setUp();

        testMessageDatabase = new MessageDatabase();
        testMessageDatabase.clearData();
        messagesearch1 = new MessageObject("hey","5556",null, true);
        numbersearch = new MessageObject("Meet me there","5554",null,false);
        testMessageDatabase.addMessage(messagesearch1);
        testMessageDatabase.addMessage(numbersearch);
        tester = getActivity();
        searchBar = (EditText) tester.findViewById(R.id.searchKeyWord);
        searchButton = (Button) tester.findViewById(R.id.searchButton);
        prevButton = (Button) tester.findViewById(R.id.prevButton);
        nextButton = (Button) tester.findViewById(R.id.nextButton);
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
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        assertEquals(1,messagesToBeDisplayed.size()); //This tests to see if the size of the list is only 1.
    }

}