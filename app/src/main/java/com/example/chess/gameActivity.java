package com.example.chess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class gameActivity extends Activity implements View.OnClickListener {

    private float[] lastClick = new float[2];
    MyCanvas myCanvas;
    DisplayMetrics metrics;
    Game g;
    int p1Avatar, p2Avatar, p1OpenAvatar, p2OpenAvatar;
    int pawnSelection;
    int pawnRow;
    int pawnCol;

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                lastClick[0] = event.getX();
                lastClick[1] = event.getY();
            }

            return false;
        }
    };
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // retrieve the stored coordinates
            float x = lastClick[0];
            float y = lastClick[1];

            // get the row & column of the click
            System.out.print("Row: ");
            System.out.println(myCanvas.getRow(lastClick[0], lastClick[1]));
            System.out.print("Col: ");
            System.out.println(myCanvas.getCol(lastClick[0], lastClick[1]));

            int row, col;
            col = myCanvas.getCol(lastClick[0], lastClick[1]);
            // row changes when flipped
            if (myCanvas.flipped) {
                row = 7 - myCanvas.getRow(lastClick[0], lastClick[1]);
            } else {
                row = myCanvas.getRow(lastClick[0], lastClick[1]);
            }

            if (pawnSelection == -1) {
                // only need to check if row is -1 since they are both -1 for invalid tile click
                // if it is a valid tile, then check to see if there is a piece there and if it is different
                // than the current piece
                if (row != -1 && g.b.tiles[row][col].value != 0 && g.b.tiles[row][col].player == g.currentPlayer) {
                    if (g.b.selected == null || g.b.selected.col != col || g.b.selected.row != row) {
                        g.b.selected = g.b.tiles[row][col];
//                    System.out.println(g.b.selected.value);
                    }
                    // if selected is the clicked piece, then deselect it by setting
                    // selected to null
                    else if (g.b.selected.col == col && g.b.selected.row == row) {
                        g.b.selected = null;
                    }
                    myCanvas.invalidate();
                } else if (g.checkSelectMove(row, col)) {
                    pawnSelection = g.doTurn(g.b.selected.row, g.b.selected.col, row, col);
                    System.out.println("P select " + pawnSelection);
                    if (pawnSelection != -1) {
                        pawnRow = row;
                        pawnCol = col;
                        myCanvas.drawPawnSelection = pawnSelection;
                    }
                    else {
                        // Don't wanna flip yet if selecting pawn
                        myCanvas.flipped = !myCanvas.flipped;
                    }
                    myCanvas.invalidate();
                }
            }
            else {
                Player p;
                if (pawnSelection == 0) {
                    p = g.p0;
                }
                else {
                    p = g.p1;
                }
                // remove pawn
                for (int i = 0; i < p.alivePieces.size(); i++) {
                    Piece piece = p.alivePieces.get(i);
                    if (piece.row == pawnRow && piece.col == pawnCol) {
                        p.alivePieces.remove(i);
                        break;
                    }
                }

                // clicks to select a new piece
                if (row == 3 || row == 4) {
                    System.out.println("made it here");
                    if (col == 0 || col == 1) {
                        g.b.tiles[pawnRow][pawnCol] = new Rook(p, pawnRow, pawnCol);
                    }
                    else if (col == 2 || col == 3) {
                        g.b.tiles[pawnRow][pawnCol] = new Knight(p, pawnRow, pawnCol);
                    }
                    else if (col == 4 || col == 5) {
                        g.b.tiles[pawnRow][pawnCol] = new Bishop(p, pawnRow, pawnCol);
                    }
                    else if (col == 6 || col == 7) {
                        g.b.tiles[pawnRow][pawnCol] = new Queen(p, pawnRow, pawnCol);
                    }
                    pawnSelection = -1;
                    myCanvas.drawPawnSelection = -1;
                    myCanvas.flipped = !myCanvas.flipped;
                    g.goToNextTurn();
                    myCanvas.invalidate();
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        // Go back to title screen if back button is pressed
        Intent TitleScreen = new Intent(gameActivity.this, MainActivity.class);

        //Launches the new activity
        startActivity(TitleScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        p1Avatar = getIntent().getIntExtra("p1Avatar", 0);
        p2Avatar = getIntent().getIntExtra("p2Avatar", 0);
        p1OpenAvatar = getIntent().getIntExtra("p1OpenAvatar", 0);
        p2OpenAvatar = getIntent().getIntExtra("p2OpenAvatar", 0);

        g = new Game(p1Avatar, p2Avatar, p1OpenAvatar, p2OpenAvatar);

        setContentView(R.layout.activity_game);
        myCanvas = new MyCanvas(this, g);

        setContentView(myCanvas);
        myCanvas.setOnTouchListener(touchListener);
        myCanvas.setOnClickListener(clickListener);

        metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        pawnSelection = -1;
    }

    @Override
    public void onClick(View v) {

    }

}