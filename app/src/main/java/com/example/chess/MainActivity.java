package com.example.chess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Scanner;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button pMove;
    private Button startGame;
    private Button displayPossibleMoves;
    private Button launchGame;
    private EditText TextBox;
    private TextView headerText;
    private TextView console;

    Board b;
    Player p0;
    Player p1;
    Game g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(this);

        pMove = (Button) findViewById(R.id.pMove);
        pMove.setOnClickListener(this);

        displayPossibleMoves = (Button) findViewById(R.id.displayPossibleMoves);
        displayPossibleMoves.setOnClickListener(this);

        launchGame = (Button) findViewById(R.id.launchGame);
        launchGame.setOnClickListener(this);

        console = (TextView) findViewById(R.id.console);
        TextBox = (EditText) findViewById(R.id.TextBox);
        headerText = (TextView) findViewById(R.id.mainText);

        p0 = new Player(0);
        p1 = new Player(1, p0);
        p0.setEnemy(p1);
        b = new Board(p0, p1);

        g = new Game();
    }

    @Override
    /*onClick is what is called when the buttons are pressed and they take in Views as arguments
     * as buttons are children of the view class, buttons can polymorphically be passed in. The button
     * that called the onClick is automatically fed in*/
    public void onClick(View v) {

        //The switch statements grab the id values of the button pressed and calculates the tip accordingly
        switch (v.getId()) {

            case R.id.startGame: {
                g.startGame();
                headerText.setText("Game Started! Player " + g.currentPlayer.getId() + "'s turn.");
                displayBoard();
                break;
            }

            case R.id.pMove: {
                String getText = TextBox.getText().toString();

                int r1 = Integer.parseInt(getText.substring(0,1));
                int c1 = Integer.parseInt(getText.substring(1,2));
                int r2 = Integer.parseInt(getText.substring(3,4));
                int c2 = Integer.parseInt(getText.substring(4,5));

                int turn_info = g.doTurn(r1, c1, r2, c2);

                if (turn_info == -1) {
                    headerText.setText("Invalid Move...Still Player " + g.currentPlayer.getId() + "'s turn.");
                    return;
                }

                // if the player successfully moved, then re-display the board
                displayBoard();

                // normal turn
                if (turn_info == 0) {
                    headerText.setText("Player " + g.currentPlayer.getId() + "'s turn.");
                }
                // checked
                else if (turn_info == 2) {
                    headerText.setText("Player " + g.currentPlayer.getId() + " is checked!");
                }
                // game over (checkmated)
                else if (turn_info == 1) {
                    headerText.setText("Player " + g.currentPlayer.getEnemy().getId() + " has won!");
                }
                break;
            }

            case R.id.displayBoard: {
                //System.out.println(g.p1.stringAlivePieces());
                //System.out.println(g.p1.dangerZoneToString(g.b));
                //System.out.println(g.b.tiles[1][1].movesToString(g.b));
                displayBoard();
                break;
            }

            case R.id.displayPossibleMoves: {
                String getText = TextBox.getText().toString();

                int r = Integer.parseInt(getText.substring(0,1));
                int c = Integer.parseInt(getText.substring(1,2));

                console.setText(g.getMoves(r,c));
                break;
            }

            case R.id.launchGame: {
                launchGameActivity();
                break;
            }
        }
    }

    public void displayBoard() {
        System.out.println(g.b.toString());
        console.setText(g.b.toString());
    }

    private void launchGameActivity()
    {
        Intent gameActivity = new Intent(MainActivity.this, gameActivity.class);

        //Launches the new activity
        startActivity(gameActivity);
    }
}

