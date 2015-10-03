package com.example.aaronplamondon.anplamon_reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class reflexGame extends AppCompatActivity {
    private static final String fileName = "reflexData.sav";
    private DataManager dataManager;
    private ReactionTimer reactionTimer;
    private Button reactionButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initiaize the variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex_game);

        reactionButton = (Button) findViewById(R.id.reactionButton);
        reactionTimer = new ReactionTimer(reactionButton);
        handler = new Handler();

        dataManager = new DataManager(this, fileName);
        dataManager.loadFromFile();

        //Set the reaction button listener
        setReactionButtonListener();

        //Set and display instruction dialog box
        createInstructionDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reflex_game, menu);
        return true;
    }

    @Override
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void createInstructionDialog() {
        //Initailize an instructions dialog box and display it to the user.
        AlertDialog.Builder dialogBox  = new AlertDialog.Builder(this);
        dialogBox.setTitle("Reaction Time Game Instructions");
        dialogBox.setMessage("This is a game of reflex. When you close this the button on the screen will prompt you to click it, and when it does you must quickly press the button.");
        dialogBox.setCancelable(false);

        // The timer will start after 500 ms to give the user some time to get ready and
        // to give enough time for the dialog box to disapear from the screen.
        dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reactionTimer.setTimerHandler();
            }
        });

        dialogBox.create().show();
    }

    private void resetTimerAndButton(Integer delayTime) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reactionButton.setText("");
                setReactionButtonListener();
                reactionTimer.setTimerHandler();
            }
        }, delayTime);
    }

    public void setReactionButtonListener() {
        //Set the reaction button listener
        reactionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reactionTimer.setEndTime(System.currentTimeMillis());
                checkReaction();
            }
        });
    }

    private void checkReaction() {
        reactionTimer.removeTimerHandler();
        reactionButton.setOnClickListener(null);
        Long timeDifference = reactionTimer.getReactionTime();

        if((reactionTimer.getStartTime() == 0L) || (timeDifference < 0)) {
            reactionButton.setText("You clicked too soon.");
            resetTimerAndButton(1000);
        } else {
            reactionButton.setText("Your time was " + Long.toString(timeDifference) + "ms! \r\n The game will soon reset.");
            dataManager.addValuesToArray(timeDifference);
            dataManager.saveToFile();
            resetTimerAndButton(1500);
        }
    }
}
