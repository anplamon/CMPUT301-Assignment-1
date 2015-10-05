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
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
Display the statistics for the reactin game and gameshow buzzers.
Reaction game statistics consist of the minimum, maximum, mean, and median reaction
times for the users past 10, past 100, and all reaction times. Gameshow statistics
will display the number of time a player clicked a button for each game mode. Game
modes are determined by the number of players playing the game.
 */

public class statistics extends AppCompatActivity {
    private DataManager reactionDataManager;
    private DataManager twoPlayerDataManager;
    private DataManager threePlayerDataManager;
    private DataManager fourPlayerDataManager;

    private ArrayList<TextView> minReactions = new ArrayList<>();
    private ArrayList<TextView> maxReactions = new ArrayList<>();
    private ArrayList<TextView> meanReactions = new ArrayList<>();
    private ArrayList<TextView> medReactions = new ArrayList<>();
    private ArrayList<TextView> twoPG = new ArrayList<>();
    private ArrayList<TextView> threePG = new ArrayList<>();
    private ArrayList<TextView> fourPG = new ArrayList<>();

    private Button clearButton;
    private Button emailButton;

    private StatisticsCalculator statisticsCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        reactionDataManager = new DataManager(this, "reflexData.sav");
        twoPlayerDataManager = new DataManager(this, "2PlayerGame.sav");
        threePlayerDataManager = new DataManager(this, "3PlayerGame.sav");
        fourPlayerDataManager = new DataManager(this, "4PlayerGame.sav");

        reactionDataManager.loadFromFile();
        twoPlayerDataManager.loadFromFile();
        threePlayerDataManager.loadFromFile();
        fourPlayerDataManager.loadFromFile();

        createArrays();

        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearButtonDialog();
            }
        });

        emailButton = (Button) findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail(statisticsMessage());
            }
        });

        statisticsCalculator = new StatisticsCalculator();
        calculateAndPrintStatistics();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    private void createArrays() {
        //Create arrays of text views.
        minReactions.add((TextView) findViewById(R.id.tenMin));
        minReactions.add((TextView) findViewById(R.id.hunMin));
        minReactions.add((TextView) findViewById(R.id.allMin));

        maxReactions.add((TextView) findViewById(R.id.tenMax));
        maxReactions.add((TextView) findViewById(R.id.hunMax));
        maxReactions.add((TextView) findViewById(R.id.allMax));

        meanReactions.add((TextView) findViewById(R.id.tenMean));
        meanReactions.add((TextView) findViewById(R.id.hunMean));
        meanReactions.add((TextView) findViewById(R.id.allMean));

        medReactions.add((TextView) findViewById(R.id.tenMed));
        medReactions.add((TextView) findViewById(R.id.hunMed));
        medReactions.add((TextView) findViewById(R.id.allMed));

        twoPG.add((TextView) findViewById(R.id.twoPGplayer1));
        twoPG.add((TextView) findViewById(R.id.twoPGplayer2));

        threePG.add((TextView) findViewById(R.id.threePGplayer1));
        threePG.add((TextView) findViewById(R.id.threePGplayer2));
        threePG.add((TextView) findViewById(R.id.threePGplayer3));

        fourPG.add((TextView) findViewById(R.id.fourPGplayer1));
        fourPG.add((TextView) findViewById(R.id.fourPGplayer2));
        fourPG.add((TextView) findViewById(R.id.fourPGplayer3));
        fourPG.add((TextView) findViewById(R.id.fourPGplayer4));
    }

    public void clearButtonDialog() {
        // Yes No Alert box from stack overflow from user Steve Haley
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        clearStatisticsData();

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        //Create a message for the dialog box so it can be centered.
        // Code for creating a message box in an alert box from stack overflow from user Zelimir
        TextView message = new TextView(this);
        message.setText("Are you sure?");
        message.setTextSize(24);
        message.setGravity(Gravity.CENTER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(message).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void clearStatisticsData() {
        //Clear the text views
        for (TextView textView : minReactions){
            textView.setText("No Data");
        }

        for (TextView textView : maxReactions){
            textView.setText("No Data");
        }

        for (TextView textView : meanReactions){
            textView.setText("No Data");
        }

        for (TextView textView : medReactions){
            textView.setText("No Data");
        }

        for (TextView textView : twoPG) {
            textView.setText("0 Buzzes");
        }

        for (TextView textView : threePG) {
            textView.setText("0 Buzzes");
        }

        for (TextView textView : fourPG) {
            textView.setText("0 Buzzes");
        }

        //Clear the actual data in each file.
        reactionDataManager.clearFile();
        twoPlayerDataManager.clearFile();
        threePlayerDataManager.clearFile();
        fourPlayerDataManager.clearFile();
    }

    public void calculateAndPrintStatistics() {
        int[] numberOfValuesArray = {10, 100, reactionDataManager.getArrayOfValues().size()};

        for (int i=0; i<3; i++) {
            minReactions.get(i).setText(statisticsCalculator.calculateMin(numberOfValuesArray[i], reactionDataManager));
            maxReactions.get(i).setText(statisticsCalculator.calculateMax(numberOfValuesArray[i], reactionDataManager));
            meanReactions.get(i).setText(statisticsCalculator.calculateMean(numberOfValuesArray[i], reactionDataManager));
            medReactions.get(i).setText(statisticsCalculator.calculateMedian(numberOfValuesArray[i], reactionDataManager));
        }

        twoPG.get(0).setText(statisticsCalculator.numberOfBuzzes(1L, twoPlayerDataManager) + " buzzes");
        twoPG.get(1).setText(statisticsCalculator.numberOfBuzzes(2L, twoPlayerDataManager) + " buzzes");

        threePG.get(0).setText(statisticsCalculator.numberOfBuzzes(1L, threePlayerDataManager) + " buzzes");
        threePG.get(1).setText(statisticsCalculator.numberOfBuzzes(2L, threePlayerDataManager) + " buzzes");
        threePG.get(2).setText(statisticsCalculator.numberOfBuzzes(3L, threePlayerDataManager) + " buzzes");

        fourPG.get(0).setText(statisticsCalculator.numberOfBuzzes(1L, fourPlayerDataManager) + " buzzes");
        fourPG.get(1).setText(statisticsCalculator.numberOfBuzzes(2L, fourPlayerDataManager) + " buzzes");
        fourPG.get(2).setText(statisticsCalculator.numberOfBuzzes(3L, fourPlayerDataManager) + " buzzes");
        fourPG.get(3).setText(statisticsCalculator.numberOfBuzzes(4L, fourPlayerDataManager) + " buzzes");
    }

    public void sendEmail(String message) {
        // create and send email
        // Code from stack overflow from user ɥʇᴉɾuɐɹ
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Statistics.");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String statisticsMessage() {
        //create email message
        String message = "Reaction Time Statistics \r\n";

        message += "Last 10 Reactions: Minimum: " + minReactions.get(0).getText()
                + " Maximum: " + maxReactions.get(0).getText()
                + " Mean: " + meanReactions.get(0).getText()
                + " Median: " + medReactions.get(0).getText() + "\r\n";

        message += "Last 100 Reactions: Minimum: " + minReactions.get(1).getText()
                + " Maximum: " + maxReactions.get(1).getText()
                + " Mean: " + meanReactions.get(1).getText()
                + " Median: " + medReactions.get(1).getText() + "\r\n";

        message += "All Reactions: Minimum: " + minReactions.get(2).getText()
                + " Maximum: " + maxReactions.get(2).getText()
                + " Mean: " + meanReactions.get(2).getText()
                + " Median: " + medReactions.get(2).getText() + "\r\n";

        message += "\r\n Gameshow Buzzer Statistics \r\n";

        message += "Two Player Buzzer Statistics: Player 1: " + twoPG.get(0).getText()
                + " Player 2: " + twoPG.get(1).getText() + "\r\n";

        message += "Three Player Buzzer Statistics: Player 1: " + threePG.get(0).getText()
                + " Player 2: " + threePG.get(1).getText()
                + " Player 3: " + threePG.get(2).getText() + "\r\n";

        message += "Four Player Buzzer Statistics: Player 1: " + fourPG.get(0).getText()
                + " Player 2: " + fourPG.get(1).getText()
                + " Player 3: " + fourPG.get(2).getText()
                + " Player 4: " + fourPG.get(3).getText() + "\r\n";

        return message;
    }

}
