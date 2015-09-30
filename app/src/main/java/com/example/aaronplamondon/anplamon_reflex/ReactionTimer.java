package com.example.aaronplamondon.anplamon_reflex;

import android.os.Handler;
import android.widget.Button;
import java.util.Random;

/**
 * Created by aaronplamondon on 2015-09-30.
 */

public class ReactionTimer {
    private Button reactionButton;
    private Handler timerHandler;
    private Integer timer;
    private Long startTime;
    private Long endTime;

    public ReactionTimer(Button reactionButton) {
        this.reactionButton = reactionButton;
        timerHandler = new Handler();
        timer = 0;
        startTime = 0L;
    }

    public void setTimerHandler() {
        //generate a random number between 10 and 2000 milliseconds
        Random random = new Random();

        timer = random.nextInt(2000 - 10 + 1) + 10;
        startTime = 0L;

        timerHandler.postDelayed(timerRunnable, timer);
    }

    public void removeTimerHandler() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    public Long getReactionTime() {
        return endTime - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            reactionButton.setText("CLICK NOW!");
            startTime = System.currentTimeMillis();
        }
    };
}
