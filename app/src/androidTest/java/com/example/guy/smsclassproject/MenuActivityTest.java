package com.example.guy.smsclassproject;
import android.content.Context;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
/**
 * Created by Guy on 11/15/2015.
 */
public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity>
{
    MenuActivity tester;
    Context context;
    private Button converstionButton;
    private Button textingButton;
    private Button draftsButton;
    private Button historyButton;
    public MenuActivityTest()
    {
        super(MenuActivity.class);

    }

    @Override
    @UiThreadTest
    protected void setUp() throws Exception
    {
        if (Looper.myLooper() == null)
        {
            Looper.prepare();
        }
        tester = getActivity();
        converstionButton = (Button) tester.findViewById(R.id.goToConversations);
        textingButton = (Button) tester.findViewById(R.id.textButton);
        draftsButton = (Button) tester.findViewById(R.id.goToDrafts);
        historyButton = (Button) tester.findViewById(R.id.goToTextHistory);


    }

    @SmallTest
    @UiThreadTest
    public void testTest()
    {
        assertNotNull(tester);
    }


}