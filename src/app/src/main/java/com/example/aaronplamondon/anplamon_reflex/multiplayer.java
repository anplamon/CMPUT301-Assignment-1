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
    private ArrayList<Integer> colours;
    private ArrayList<String> fileNames;
    private Integer numberOfPlayers = 2;
    private Integer seekBarStep = 33;
    private Integer maxHeightOfButton = 440;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        createColoursArray();
        createFileNamesArray();

        playerBar = (SeekBar) findViewById(R.id.playerBar);
        playerBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            Integer progressValue = 0;

            // Code from stack overflow from user jimpanzer
            @Override
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
        Integer buttonHeight = Math.round(maxHeightOfButton / numberOfPlayers);
        String fileName = fileNames.get(numberOfPlayers - 2);

        for (int i = 0; i < numberOfPlayers;i++) {
            PlayerButton playerButton = new PlayerButton(this, (long) i + 1, fileName);
            playerButton.initialize(dpToPx(buttonHeight), (float) dpToPx(70 + buttonHeight * (i)));
            playerButton.setColour(colours.get(i));
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

    private void createColoursArray() {
        colours = new ArrayList<>();
        colours.add(Color.RED);
        colours.add(Color.CYAN);
        colours.add(Color.GREEN);
        colours.add(Color.YELLOW);
    }

    private void createFileNamesArray() {
        fileNames = new ArrayList<>();
        fileNames.add("2PlayerGame.sav");
        fileNames.add("3PlayerGame.sav");
        fileNames.add("4PlayerGame.sav");
    }

    // Code from stack overflow from user Bachi
    private int dpToPx(int dp) {
        // Method to convert dp to pixel
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
