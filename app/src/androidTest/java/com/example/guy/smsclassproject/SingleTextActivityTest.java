package com.example.guy.smsclassproject;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.*;
import android.widget.*;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by gamalieljrz on 11/10/2015.
 */
public class SingleTextActivityTest
    extends ActivityInstrumentationTestCase2<SingleTextActivity>{

    public SingleTextActivityTest(){
        super(SingleTextActivity.class);
    }
    private SingleTextActivity tester;
    private MessageObject theMessagetest;
    private TextView singleText;
    private MessageDatabase testmessageDatabase;
    private Button deleteButton;
    private ZoomControls zoomControls;
    private float textSize;
    private float previoustextSize;
    static private ArrayList<MessageObject> deleteFromThisList;

    @BeforeClass
    public void setUp() throws Exception{
        super.setUp();
        tester = new SingleTextActivity();
        testmessageDatabase = new MessageDatabase();
        theMessagetest = new MessageObject("I'm a test", "5554",null, false);
        testmessageDatabase.addMessage(theMessagetest);
        singleText = (TextView)tester.findViewById(R.id.viewMessage);
        deleteButton = (Button)tester.findViewById(R.id.deleteButton);
        zoomControls = (ZoomControls)tester.findViewById(R.id.zoomControls);
        /*deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tester.deleteMessage();
            }
        });
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tester.zoomIn();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tester.zoomOut();
            }
        });*/
    }
    @Test
    public void testButtons(){
        assertNotNull(tester.deleteButton);

        previoustextSize = textSize;
        assertTrue(zoomControls.performClick());
        zoomControls.performClick();
        assertNotSame(textSize, previoustextSize);

    }
    @Test
    public void testDelete(){
        assertTrue(deleteButton.performClick());
        testmessageDatabase.deleteMessage(theMessagetest);
        //check to see that message was in fact deleted;
        assertNull(testmessageDatabase);
    }


}
