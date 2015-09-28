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
import java.util.Random;

public class reflexGame extends AppCompatActivity {

    private Handler timerHandler;
    private Button reactionButton;
    private Integer timer = 0;
    private Long oldTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initiaize the variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex_game);
        timerHandler = new Handler();
        reactionButton = (Button) findViewById(R.id.reactionButton);

        //Set the reaction button listener
        setReactionButtonListener();

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
                //start reaction time game.
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setReactionTimer();
                    }
                }, 500);
            }
        });

        dialogBox.create().show();
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

    private void setReactionTimer() {
        //generate a random number between 10 and 2000 milliseconds
        Random random = new Random();

        timer = random.nextInt(2000 - 10 + 1) + 10;
        oldTime = 0L;

        timerHandler.postDelayed(timerRunnable, timer);
    }

    private void checkReaction(Long oldTime, Long newTime) {
        timerHandler.removeCallbacks(timerRunnable);
        reactionButton.setOnClickListener(null);
        Long timeDifference = newTime - oldTime;

        if((oldTime == 0L) || (timeDifference < 0)) {
            resetTimerAndButton("You clicked too soon.", 1000);
        } else {
            resetTimerAndButton("Your time was " + Long.toString(timeDifference) + "ms! \r\n The game will soon reset.",1500);
        }

    }

    private void resetTimerAndButton(String buttonText, Integer delayTime) {
        reactionButton.setText(buttonText);
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reactionButton.setText("");
                setReactionButtonListener();
                setReactionTimer();
            }
        }, delayTime);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            reactionButton.setText("CLICK NOW!");
            oldTime = System.currentTimeMillis();
        }
    };


    private void setReactionButtonListener() {
        //Set the reaction button listener
        reactionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkReaction(oldTime, System.currentTimeMillis());
            }
        });
    }
}
