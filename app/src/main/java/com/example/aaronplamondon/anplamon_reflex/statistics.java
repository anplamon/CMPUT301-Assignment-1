package com.example.aaronplamondon.anplamon_reflex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class statistics extends AppCompatActivity {
    private DataManager reactionDataManager;
    private DataManager twoPlayerDataManager;
    private DataManager threePlayerDataManager;
    private DataManager fourPlayerDataManager;

    private ArrayList<TextView> minReactions = new ArrayList<>();
    private ArrayList<TextView> maxReactions = new ArrayList<>();
    private ArrayList<TextView> meanReactions = new ArrayList<>();
    private ArrayList<TextView> medReactions = new ArrayList<>();

    private Button clearButton;

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

        createReactionTimeArrays();

        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearButtonDialog();
            }
        });

        statisticsCalculator = new StatisticsCalculator();
        calculateAndPrintStatistics(reactionDataManager);

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

    private void createReactionTimeArrays() {
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
    }

    //http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-in-android
    public void clearButtonDialog() {
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
        //http://stackoverflow.com/questions/4954130/center-message-in-android-dialog-box
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
            textView.setText("");
        }

        for (TextView textView : maxReactions){
            textView.setText("");
        }

        for (TextView textView : meanReactions){
            textView.setText("");
        }

        for (TextView textView : medReactions){
            textView.setText("");
        }

        //Clear the actual data in each file.
        reactionDataManager.clearFile();
        twoPlayerDataManager.clearFile();
        threePlayerDataManager.clearFile();
        fourPlayerDataManager.clearFile();
    }

    public void calculateAndPrintStatistics(DataManager dataManager) {
        int[] numberOfValuesArray = {10, 100, dataManager.getArrayOfValues().size()};

        for (int i=0; i<3; i++) {
            minReactions.get(i).setText(statisticsCalculator.calculateMin(numberOfValuesArray[i], dataManager) + "ms");
            maxReactions.get(i).setText(statisticsCalculator.calculateMax(numberOfValuesArray[i], dataManager) + "ms");
            meanReactions.get(i).setText(statisticsCalculator.calculateMean(numberOfValuesArray[i], dataManager) + "ms");
            medReactions.get(i).setText(statisticsCalculator.calculateMedian(numberOfValuesArray[i], dataManager) + "ms");
        }
    }

}
