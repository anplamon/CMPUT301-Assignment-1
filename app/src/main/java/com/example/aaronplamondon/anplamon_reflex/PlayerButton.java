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

import java.util.jar.Attributes;

/**
 * Created by aaronplamondon on 2015-09-29.
 */
public class PlayerButton extends Button {
    private Integer playerNumber;
    private RelativeLayout.LayoutParams params;

    public PlayerButton(Context context, Integer playerNumber, Integer height) {
        super(context);
        this.playerNumber = playerNumber;
        params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
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

    public void setButtonHeight(Integer height) {
        this.setHeight(height);
    }

    public void setButtonText() {
        this.setText("Player " + Integer.toString(playerNumber));
    }

    public RelativeLayout.LayoutParams getLayoutParameters() {
        return params;
    }

    public void setOnClickListener() {
        this.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDialogBox();
            }
        });
    }

    private void openDialogBox() {
        //create a message to pu into a dialog box
        //http://stackoverflow.com/questions/4954130/center-message-in-android-dialog-box
        TextView message = new TextView(this.getContext());
        message.setText("Player " + Integer.toString(playerNumber) + "'s turn.");
        message.setTextSize(24);
        message.setGravity(Gravity.BOTTOM);

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
}
