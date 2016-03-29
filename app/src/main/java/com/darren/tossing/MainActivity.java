package com.darren.tossing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * In this project we are not generating the layouts using java code,
 * we are using xml to create out layout design,
 * xml code can be found at res>layout>activity_main.xml
 * all layout changes can be directly done there
 * Activity implements OnClickListener to handle the click events in the app page
 */

public class MainActivity extends AppCompatActivity implements OnClickListener {

    /**
     * In this project we are creating three screens
     * 1. Welcome screen
     * 2. Waiting screen
     * 3. Result screen
     * In place of creating separate pages for all three screens, we will create separate layouts for each screen
     * and will use visibility function for changing the screens
     */

    /**
     * Declaration of relativeLayouts
     * Each relative layout represents each screen here
     * rlWelcome: Welcome screen
     * rlWaiting: Waiting screen
     * rlResult: Result screen
     */
    RelativeLayout rlWelcome, rlWaiting, rlResult;

    /**
     * Declaration of imageViews used in all screens
     * ivThumbsUp && ivThumbsDown: Used in welcome screen as option for heads and tails
     * ivWaiting: Waiting image used in waiting screen
     * ivResult: Result images used in result screen
     */
    ImageView ivThumbsUp, ivThumbsDown, ivWaiting, ivResult;

    /**
     * Declaration of textViews which we will modify during execution of app
     * tvResult: To show result message in result screen
     * tvTop: to show header line in result screen
     */
    TextView tvResult, tvTop;

    /**
     * Declaration of Button
     * btReset: For resetting all screens to start over
     */
    Button btReset;

    /**
     * Declaration of required strings
     * selectedOption: for storing selected option on first screen
     * result: for storing result based on random number generated
     */
    String selectedOption, result;

    /**
     * Random Object for generating random number in given range
     * Note: Random is inbuilt class of java
     */
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Here we are setting out xml layout to activity class
         */
        setContentView(R.layout.activity_main);

        /**
         * Toolbar is the header Bar which is shows at the top of the screen with the app name
         * Setting toolbar to the activity using setSupportActionBar method
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Initializing all Relative Layout (screen) from the xml layout
         */
        rlWelcome = (RelativeLayout) findViewById(R.id.rlLayout1);
        rlWaiting = (RelativeLayout) findViewById(R.id.rlLayout2);
        rlResult = (RelativeLayout) findViewById(R.id.rlLayout3);

        /**
         * Initializing all the imageViews from the xml layout
         */
        ivThumbsUp = (ImageView) findViewById(R.id.ivThumbsUp);
        ivThumbsDown = (ImageView) findViewById(R.id.ivThumbsDown);
        ivWaiting = (ImageView) findViewById(R.id.ivWaiting);
        ivResult = (ImageView) findViewById(R.id.ivResult);

        /**
         * Initializing all the textViews from the xml layout
         */
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvTop = (TextView) findViewById(R.id.tvTop);

        /**
         * Initializing buttons from xml layout
         */
        btReset = (Button) findViewById(R.id.btReset);


        /**
         * Assigning click events listener to all the required widgets
         * In this app we have three click events
         * 1. On selection of thumbsUp or thumbsDown in welcome screen
         * 2. On pressing resest button on results page
         */
        ivThumbsUp.setOnClickListener(this);
        ivThumbsDown.setOnClickListener(this);
        btReset.setOnClickListener(this);

    }

    /**
     * Generating random number in range 1-100
     * Checking number
     * if even then storing result as "heads"
     * else if odd storing result as "tails"
     */
    private void generateRandomNumber(){
        int number = 1 + random.nextInt(100 - 1 + 1);
        if(number % 2 == 0) {
            result = "heads";
        } else {
            result = "tails";
        }
        rlWelcome.setVisibility(View.GONE);
        rlWaiting.setVisibility(View.VISIBLE);
        showWaitingScreen();
    }

    /**
     * Showing waiting screen for 2 seconds
     * after 3 seconds populate result in resultScreen
     * hide waiting screen
     */
    private void showWaitingScreen(){
        Handler handler = new Handler();
        final Runnable r = new Runnable(){
            public void run() {
                rlWaiting.setVisibility(View.GONE);
                populateResult();
            }
        };

        handler.postDelayed(r, 2000);   //You can change waiting time as per your need, time is in "milliseconds"
    }

    /**
     * Populating result screen
     * if selected option and random result option is same
     * then show you won
     * else show you lost
     * show result screen
     */
    private void populateResult(){
        if(result.equalsIgnoreCase(selectedOption)) {
            tvTop.setText("Yippee!!!");
            ivResult.setImageResource(R.drawable.won);
            tvResult.setText("Congrats you won!!!");
        } else {
            tvTop.setText("oOps!!!");
            ivResult.setImageResource(R.drawable.ops);
            tvResult.setText("You lost!!! Try again.");
        }
        rlResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        /**
         * v.getId() method returns the id of the widget
         * This id is used for identifying the click event
         */
        switch (v.getId()) {

            case R.id.ivThumbsUp: //For thumbsUp click listener
                selectedOption = "heads"; // storing selection to variable
                generateRandomNumber();
                break;

            case R.id.ivThumbsDown: //For thumbsDown click listener
                selectedOption = "tails"; //storing selection to variable
                generateRandomNumber();
                break;

            case R.id.btReset: //For reset button click listener
                rlResult.setVisibility(View.GONE); //Hiding resultScreen
                rlWaiting.setVisibility(View.GONE); //Hiding waitingScreen
                rlWelcome.setVisibility(View.VISIBLE); //Showing Welcome screen
                break;

        }
    }


    /** On BackPress Start (Optional) **/

    /**
     * Note: Additional Feature, OPTIONAL
     * If don't need this just remove the code from start of this comment
     * to the last closing braces
     */

    /**
     * OnBackPressed Flag
     */
    boolean doubleBackToExitPressedOnce = false;

    /**
     * On pressing back button of android device
     * On pressing back button show a message "Press back again to exit"
     * if again back is pressed in 3 seconds then exit the app
     * else reset the backButton flag
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }

    /** On BackPress ENd (Optional) **/

}
