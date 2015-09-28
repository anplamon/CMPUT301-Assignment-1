package com.example.aaronplamondon.anplamon_reflex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class multiplayer extends AppCompatActivity {
    private SeekBar playerBar;
    private TextView textView;
    private Integer numberOfPlayers;
    private Integer seekBarStep = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        textView = (TextView) findViewById(R.id.textView);
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
                numberOfPlayers = progressValue + 2;
                seekBar.setProgress(progressValue);
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
}
