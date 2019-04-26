package com.example.chess;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button p1Move;
    private Button p2Move;
    private Button displayBoard;
    private Button startGame;

    private TextView console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1Move = (Button) findViewById(R.id.p1Move);
        p1Move.setOnClickListener(this);

        p2Move = (Button) findViewById(R.id.p2Move);
        p2Move.setOnClickListener(this);

        displayBoard = (Button) findViewById(R.id.displayBoard);
        displayBoard.setOnClickListener(this);

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(this);

        console = (TextView) findViewById(R.id.console);
        console.setOnClickListener(this);
    }

    @Override
    /*onClick is what is called when the buttons are pressed and they take in Views as arguments
     * as buttons are children of the view class, buttons can polymorphically be passed in. The button
     * that called the onClick is automatically fed in*/
    public void onClick(View v) {

        //The switch statements grab the id values of the button pressed and calculates the tip accordingly
        switch (v.getId()) {

            case R.id.p1Move: {
                console.setText("□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n□□□□□□□□\n");
                break;
            }

            case R.id.p2Move: {
                break;
            }

            case R.id.displayBoard: {
                Board b = new Board();
                System.out.println(b.toString());
                System.out.println(b.tiles[0][3].getPossibleMoves(b));
                break;
            }
        }
    }
}

