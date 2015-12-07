package com.example.guy.smsclassproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private int page;
    private TextView pageNumber;
    Button[] correspondentButtons;
    private MessageDatabase messageDatabase;

    public ArrayList<MessageObject> messagesToBeDisplayed;

    /**
     * Find all the Views of the MenuActivity.xml and give all the Buttons their
     * onClickListeners
     * @Param savedInstanceState The Bundle that is needed to call the super.onCreate().
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        prevButton = (Button)findViewById(R.id.prevButton);
        nextButton = (Button)findViewById(R.id.nextButton);
        pageNumber = (TextView)findViewById(R.id.pageNumber);
        prevButton.setOnClickListener(new MessageOnClickListener());
        nextButton.setOnClickListener(new MessageOnClickListener());
        initializeMessageButtons();
    }

    /**
     * assigns actions to all the buttons
     */
    private class MessageOnClickListener implements View.OnClickListener {
        public void onClick(View v)
        {
            if(v==prevButton)
            {
                if(page<=0)
                    return;
                page--;
                redisplayTexts();
                return;
            }
            if(v==nextButton)
            {
                if((messagesToBeDisplayed.size()-1)/10<=page)
                    return;
                page++;
                redisplayTexts();
                return;
            }
            int loc = 0;
            for(; loc<10; loc++)
            {
                if(correspondentButtons[loc]==v)
                    break;
            }
            int messageIndex = loc+10*page;
            if(messageIndex>=messagesToBeDisplayed.size())
                return;
            MessageObject message = messagesToBeDisplayed.get(messageIndex);
            TextingActivity.currentNumber = message.getNumber();
            page=0;//Leave
            redisplayTexts();//Leave
            startActivity(new Intent(ConversationActivity.this, TextingActivity.class));//Change




        }
    }

    /**
     * creates and sets up the buttons used to display the conversations
     */
    private void initializeMessageButtons()
    {
        correspondentButtons = new Button[10];
        correspondentButtons[0] = (Button)findViewById(R.id.textButton1);
        correspondentButtons[1] = (Button)findViewById(R.id.textButton2);
        correspondentButtons[2] = (Button)findViewById(R.id.textButton3);
        correspondentButtons[3] = (Button)findViewById(R.id.textButton4);
        correspondentButtons[4] = (Button)findViewById(R.id.textButton5);
        correspondentButtons[5] = (Button)findViewById(R.id.textButton6);
        correspondentButtons[6] = (Button)findViewById(R.id.textButton7);
        correspondentButtons[7] = (Button)findViewById(R.id.textButton8);
        correspondentButtons[8] = (Button)findViewById(R.id.textButton9);
        correspondentButtons[9] = (Button)findViewById(R.id.textButton10);
        for(Button button : correspondentButtons)
        {
            button.setTransformationMethod(null);
            button.setOnClickListener(new MessageOnClickListener());
        }
    }
    @Override
    /**
     * adds options to the mnu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    /**
     * adds options to the mnu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * What the page does of closed and reopened
     */
    protected void onResume()
    {
        if(messageDatabase==null)
            messageDatabase = new MessageDatabase();
        messagesToBeDisplayed = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<MessageObject> allMessages =  messageDatabase.getAllTexts();
        for(MessageObject messageObject : allMessages)
        {
            if(shouldAddMessage(numbers,messageObject))
            {
                messagesToBeDisplayed.add(messageObject);
            }
        }
        redisplayTexts();
        super.onResume();
    }

    /**
     * determines if number is one that needs to be added to conversation view
     * @Param numbers, an array list of the phone numers. messageObject is the message being checked
     */
    private boolean shouldAddMessage(ArrayList<String> numbers, MessageObject messageObject)
    {
        String messageNumber = messageObject.getNumber();
        for(String number: numbers)
        {
            if(Database.isSameNumber(number,messageNumber))
                return false;
        }
        numbers.add(messageNumber);
        return true;
    }

    /**
     * displays messages in groups of ten
     */
    private void redisplayTexts()
    {

        int amountOfMessages = messagesToBeDisplayed.size();
        for(int i = 0; i<10; i++)
        {
            if(i+10*page>=amountOfMessages)
            {
                clearTheRest(i);
                break;
            }
            MessageObject message = messagesToBeDisplayed.get(i+10*page);
            String str = TextingActivity.formatedNumber(message.getNumber())+": ";
            str +=message.getSmsMessage();
            correspondentButtons[i].setText(str);
        }
        pageNumber.setText("" + (page + 1));
    }
    /**
     * clears text from buttons, mostly used when going to a new
     * page of results
     * @param startButton button to start clearing from
     */
    private void clearTheRest(int startButton)
    {
        for(int i =  startButton; i<10; i++)
        {
            correspondentButtons[i].setText("");
        }
    }

}
