package com.example.guy.smsclassproject;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by Guy on 11/15/2015.
 */
public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity>
{
    MenuActivity tester = new MenuActivity();
    Context context;
    public MenuActivityTest()
    {
        super(MenuActivity.class);

    }

    @Override
    protected void setUp() throws Exception
    {
        tester = getActivity();
        testTest();
    }

    @Test
    public void testTest()
    {
        assertTrue(tester == null);
    }

}