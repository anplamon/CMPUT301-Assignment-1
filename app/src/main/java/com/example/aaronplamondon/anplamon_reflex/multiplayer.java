package com.example.aaronplamondon.anplamon_reflex;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.ArrayList;

public class multiplayer extends AppCompatActivity {
    private SeekBar playerBar;
    private Button createBuzzerButton;
    private ArrayList<PlayerButton> playerButtons = new ArrayList<>();
    private Integer numberOfPlayers = 2;
    private Integer seekBarStep = 33;
    private Integer maxHeight = 440;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        playerBar = (SeekBar) findViewById(R.id.playerBar);
        playerBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            Integer progressValue = 0;

            @Override
            //on progress method from http://stackoverflow.com/questions/16272053/how-to-increment-seek-bar-value
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressValue = ((int) Math.round(progressValue / (double) seekBarStep)) * seekBarStep;
                numberOfPlayers = progressValue/33 + 2;
                createBuzzerButton.setText(Integer.toString(numberOfPlayers) + " Players");
                seekBar.setProgress(progressValue);
            }
        });

        createBuzzerButton = (Button) findViewById(R.id.createBuzzerButton);
        createBuzzerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removePlayerButtons();
                createPlayerButtons();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer, menu);
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

    private void createPlayerButtons() {
        //create N instances of player butons, where N is the number of players chosen by the seekBar
        Integer buttonHeight = Math.round(maxHeight / numberOfPlayers);
        ArrayList<Integer> colours = createColoursArray();

        for (int i = 1; i <= numberOfPlayers;i++) {
            PlayerButton playerButton = new PlayerButton(this, i, dpToPx(buttonHeight), (float) dpToPx(70 + buttonHeight * (i - 1)));
            playerButton.setColour(colours.get(i-1));
            playerButton.displayButton();
            addContentView(playerButton, playerButton.getLayoutParameters());
            playerButtons.add(playerButton);
        }
    }

    private void removePlayerButtons() {
        for (PlayerButton playerButton : playerButtons) {
            playerButton.stopOnClickListener();
            playerButton.destroyButton();
        }
        playerButtons = new ArrayList<>();
    }

    private ArrayList<Integer> createColoursArray() {
        ArrayList<Integer> colours = new ArrayList<>();
        colours.add(Color.RED);
        colours.add(Color.CYAN);
        colours.add(Color.GREEN);
        colours.add(Color.YELLOW);
        return colours;
    }

    // Method from http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
