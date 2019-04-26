package com.example.chess;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    public gameActivity() {

    }

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

            int row = myCanvas.getRow(lastClick[0], lastClick[1]);
            int col = myCanvas.getCol(lastClick[0], lastClick[1]);


            // only need to check if row is -1 since they are both -1 for invalid tile click
            // if it is a valid tile, then check to see if there is a piece there and if it is different
            // than the current piece
            if (row != -1 && g.b.tiles[row][col].value != 0 && g.b.tiles[row][col].player == g.currentPlayer){
                if (g.b.selected == null || g.b.selected.col != col || g.b.selected.row != row) {
                    g.b.selected = g.b.tiles[row][col];
                    System.out.println(g.b.selected.value);
                }
                // if selected is the clicked piece, then deselect it by setting
                // selected to null
                else if (g.b.selected.col == col && g.b.selected.row == row) {
                    g.b.selected = null;
                }
                myCanvas.invalidate();
            }
            else if (g.checkSelectMove(row, col)) {
                g.doTurn(g.b.selected.row, g.b.selected.col, row, col);
                myCanvas.invalidate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        g = new Game();

        setContentView(R.layout.activity_game);
        myCanvas = new MyCanvas(this, g);

        setContentView(myCanvas);
        myCanvas.setOnTouchListener(touchListener);
        myCanvas.setOnClickListener(clickListener);

        metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
    }

    @Override
    public void onClick(View v) {

    }
}