package com.example.chess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Scanner;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button launchGame;
    private TextView headerText;
    private Switch switchAdvanced;
    public static boolean isAdvanced;

    Board b;
    Player p0;
    Player p1;
    Game g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchGame = (Button) findViewById(R.id.launchGame);
        launchGame.setOnClickListener(this);

        switchAdvanced = (Switch) findViewById(R.id.switchAdvanced);
        switchAdvanced.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAdvanced = false;
                if (isChecked) {
                    isAdvanced = true;
                }

            }
        });

        headerText = (TextView) findViewById(R.id.mainText);
    }

    @Override
    public void onClick(View v) {

        //The switch statements grab the id values of the button pressed and calculates the tip accordingly
        switch (v.getId()) {

            case R.id.launchGame: {
                launchGameActivity();
                break;
            }
        }
    }

    public void displayBoard() {
        System.out.println(g.b.toString());
    }

    private void launchGameActivity()
    {
        Intent chooseActivity = new Intent(MainActivity.this, chooseActivity.class);

        //Launches the new activity
        startActivity(chooseActivity);
    }
}

