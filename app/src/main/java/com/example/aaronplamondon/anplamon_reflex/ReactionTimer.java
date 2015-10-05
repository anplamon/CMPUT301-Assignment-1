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
