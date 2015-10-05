/**********************************************
 This file is part of anplamon-reflex.

 anplamon-reflex is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 anplamon-reflex is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 Copyright (c) 2015 Aaron Plamondon

 You should have received a copy of the GNU General Public License
 along with anplamon-reflex.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************/

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

/*
Reaction based game where the user waits between 10-2000ms before the button
tells the user to click it. their reaction time is recorded and displayed on
the button.
 */

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
        setTimerAndButton();

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
                setTimerAndButton();
                reactionTimer.setTimerHandler();
            }
        }, delayTime);
    }

    private void setTimerAndButton() {
        //Set the reaction button listener
        reactionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reactionTimer.setEndTime(System.currentTimeMillis());
                checkReaction();
            }
        });
    }

    private void checkReaction() {
        //Check the users reaction time. If the start time is 0 or time difference
        // is negative then the user pressed the button too early so it will complain
        // and restart the game after 1 second, otherwise it will display the reaction
        // time and save it in the data manager.

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
