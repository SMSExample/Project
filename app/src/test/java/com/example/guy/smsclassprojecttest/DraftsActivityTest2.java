package com.example.guy.smsclassprojecttest;

import android.content.Intent;
import android.provider.Telephony;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
//import android.support.test.rule.ActivityTestRule;
import com.example.guy.smsclassproject.*;

import org.junit.*;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;


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

   // @Rule
   // public ActivityTestRule<DraftsActivity> mActivityRule = new ActivityTestRule<>(DraftsActivity.class);

   @Before
    public void setUp() throws Exception
    {
        draftsDatabase = new DraftsDatabase();
        MessageObject messageObject1 = new MessageObject("hi", "5554", true);
        MessageObject messageObject2 = new MessageObject("hi hi", "5555554", true);
        MessageObject messageObject3 = new MessageObject("sup", "5435555554", true);
        draftsDatabase.addMessage(messageObject1);
        draftsDatabase.addMessage(messageObject2);
        draftsDatabase.addMessage(messageObject3);
        messagesToBeDisplayed = draftsDatabase.getAllTexts();
        tester = getActivity();
        messagesToBeDisplayed = tester.messagesToBeDisplayed;
        searchText = (EditText) tester.findViewById(R.id.searchText);
        searchButton = (Button) tester.findViewById(R.id.searchButton);
    }

   @Test
    public void testSearch() {

        searchText.setText("hi");
        searchButton.performClick();
        messagesToBeDisplayed = draftsDatabase.getMessagesByKey(searchText.getText().toString());
        Assert.assertEquals(4,3);
        Assert.assertEquals("Messages with the word hi", 2, messagesToBeDisplayed.size());
        Assert.assertTrue(false);
    }
}
