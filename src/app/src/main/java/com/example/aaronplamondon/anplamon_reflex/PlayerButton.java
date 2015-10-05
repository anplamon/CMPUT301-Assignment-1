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
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by aaronplamondon on 2015-09-29.
 */

/*
A generic player button for the gameshow activity. It is given a player number.
When clicked it will display its player number and record that number in the
passed in data manager.
 */

public class PlayerButton extends Button {
    private DataManager dataManager;
    private Long playerNumber;
    private RelativeLayout.LayoutParams params;

    public PlayerButton(Context context, Long playerNumber, String fileName) {
        //Initialize attributes
        super(context);
        this.playerNumber = playerNumber;
        dataManager = new DataManager(context, fileName);
    }

    public void initialize(Integer height, Float yCoordinate) {
        params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        //Initialize other instances of the player button
        this.setY(yCoordinate);
        this.setButtonLayout();
        this.setButtonText();
        this.setOnClickListener();
    }

    public void setColour(Integer colour) {
        this.setBackgroundColor(colour);
    }

    public void setButtonLayout() {
        this.setLayoutParams(params);
    }

    public void setButtonText() {
        this.setText("Player " + Long.toString(playerNumber));
    }

    public RelativeLayout.LayoutParams getLayoutParameters() {
        return params;
    }

    public void setOnClickListener() {
        this.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDialogBox();
                savePlayerBuzz();
            }
        });
    }

    public void stopOnClickListener() {
        this.setOnClickListener(null);
    }

    private void openDialogBox() {
        //create a message to pu into a dialog box
        // Code for creating a message box in an alert box from stack overflow from user Zelimir
        TextView message = new TextView(this.getContext());
        message.setText("Player " + Long.toString(playerNumber) + "'s turn.");
        message.setTextSize(24);
        message.setGravity(Gravity.CENTER);

        //create and display dialog box
        AlertDialog.Builder dialogBox  = new AlertDialog.Builder(this.getContext());
        dialogBox.setView(message);
        dialogBox.setCancelable(false);

        dialogBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBox.create().show();
    }

    public void displayButton() {
        this.setVisibility(View.VISIBLE);
    }

    public void destroyButton() {
        this.setVisibility(View.GONE);
    }

    public void savePlayerBuzz() {
        dataManager.loadFromFile();
        dataManager.addValuesToArray(playerNumber);
        dataManager.saveToFile();
    }
}
