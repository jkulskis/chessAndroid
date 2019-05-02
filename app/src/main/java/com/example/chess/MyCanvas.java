package com.example.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class MyCanvas extends View {

    Game g;
    Paint paint;
    DisplayMetrics metrics;
    int width, height;
    int tileSideLength;
    int[][] vertLines, horizLines;
    int verticalPadding;
    Bitmap p0Avatar, p1Avatar, openp0Avatar, openp1Avatar;
    boolean flipped;
    int drawPawnSelection;
    int victoryXScalar;
    boolean victoryDanceRight;


    public MyCanvas(Context context, Game g) {
        super(context);

        drawPawnSelection = -1;
        this.g = g;
        paint = new Paint();

        p0Avatar = getBitmapFromVectorDrawable(getContext(), g.p0.avatarId);
        p1Avatar = getBitmapFromVectorDrawable(getContext(), g.p1.avatarId);
        openp0Avatar = getBitmapFromVectorDrawable(getContext(), g.p0.openAvatarId);
        openp1Avatar = getBitmapFromVectorDrawable(getContext(), g.p1.openAvatarId);

        metrics = context.getResources().getDisplayMetrics();
        setWillNotDraw(false);

        width = metrics.widthPixels;
        height = metrics.heightPixels;

        tileSideLength = width / 8;
        // assume in vertical mode, so height > width
        verticalPadding = (height - width) / 2;
    }

    public void configurePaint() {
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // flip the board after every turn
        if (flipped) {
            canvas.scale(1, -1, width / 2, height / 2);
        }
        super.onDraw(canvas);
        configurePaint();

        // set background to #4A0C21
        canvas.drawColor(Color.parseColor("#4A0C21"));



        if (g.currentPlayer.checkmated == false) {
            // draw the avatars
            drawAvatar(g.p0, canvas, false, false);
            drawAvatar(g.p1, canvas, false, false);
        }
        drawCheckeredTiles(canvas);
        // If checked, draw a pink square where the current player king is
        if (g.currentPlayer.checkmated) {
            fillKingSquare(canvas, true);
            drawAvatar(g.currentPlayer.getEnemy(), canvas, false, true);
            drawAvatar(g.currentPlayer, canvas, false, false);
        } else if (g.currentPlayer.checked) {
            fillKingSquare(canvas, false);
        }
        if (drawPawnSelection == -1) {
            if (MainActivity.isAdvanced == false) {
                drawSelectedMoves(canvas);
                configurePaint();
            } else {
                drawSelectedPiece(canvas);
                configurePaint();
            }
        }

        // need 9 horizontal lines and 9 vertical lines to draw the board
        horizLines = new int[9][4];
        vertLines = new int[9][4];

        // Find and store the coordinates for the vertical lines
        for (int i = 0; i < 9; i++) {
            int[] newCoord = {tileSideLength * i, height - verticalPadding, tileSideLength * i, verticalPadding};
            vertLines[i] = newCoord;
        }
        // Find and store the coordinates for the horizontal lines
        for (int i = 0; i < 9; i++) {
            int[] newCoord = {0, verticalPadding + tileSideLength * i, width, verticalPadding + tileSideLength * i};
            horizLines[i] = newCoord;
            horizLines[i] = newCoord;
        }

        // Actually draw the lines
        for (int i = 0; i < vertLines.length; i++) {
            canvas.drawLine(vertLines[i][0], vertLines[i][1], vertLines[i][2], vertLines[i][3], paint);
        }
        for (int i = 0; i < horizLines.length; i++) {
            canvas.drawLine(horizLines[i][0], horizLines[i][1], horizLines[i][2], horizLines[i][3], paint);
        }

        if (drawPawnSelection == -1) {
            // Draw the alive pieces (P0)
            for (int i = 0; i < g.p0.alivePieces.size(); i++) {
                drawAlivePiece(g.p0.alivePieces.get(i), canvas);
            }
            // Draw the alive pieces (P1)
            for (int i = 0; i < g.p1.alivePieces.size(); i++) {
                drawAlivePiece(g.p1.alivePieces.get(i), canvas);
            }
        }
        else {
            drawPawnSelection(canvas);
        }

        drawDeadPieces(g.p0, g.p0.deadPieces, canvas);
        drawDeadPieces(g.p1, g.p1.deadPieces, canvas);
    }

    public void drawPawnSelection(Canvas canvas) {
        Piece[] list = new Piece[4];
        Player p = new Player(drawPawnSelection);
        list[0] = new Rook(p,0, 0);
        list[1] = new Knight(p,0, 0);
        list[2] = new Bishop(p, 0, 0);
        list[3] = new Queen(p, 0, 0);
        RectF rect;
        for (int i = 0; i < 8; i += 2) {
            if (flipped) {
                rect = new RectF(tileSideLength * i, verticalPadding + tileSideLength * 5,
                        tileSideLength * (i + 2), verticalPadding + tileSideLength * 3);
            } else {
                rect = new RectF(tileSideLength * i, verticalPadding + tileSideLength * 3,
                        tileSideLength * (i + 2), verticalPadding + tileSideLength * 5);
            }
            Bitmap b = BitmapFactory.decodeResource(getResources(), getImageId(list[i/2]));
            canvas.drawBitmap(b, null, rect, null);
        }
    }
    // if checkmate == true, then draw a red square ... otherwise draw a pink square (for check)
    public void fillKingSquare(Canvas canvas, boolean checkmate) {

        for (int i = 0; i < g.currentPlayer.alivePieces.size(); i++) {
            if (g.currentPlayer.alivePieces.get(i).type.equals("King")) {
                if (checkmate)
                    paint.setColor(Color.rgb(255, 0, 0));
                else
                    paint.setColor(Color.rgb(225, 135, 174));
                RectF rect;
                System.out.println("made it check");
                rect = new RectF(tileSideLength * g.currentPlayer.alivePieces.get(i).col, verticalPadding + g.currentPlayer.alivePieces.get(i).row * tileSideLength,
                            tileSideLength * (g.currentPlayer.alivePieces.get(i).col + 1), verticalPadding + tileSideLength * (g.currentPlayer.alivePieces.get(i).row + 1));

                canvas.drawRect(rect, paint);
                break;
            }
        }
    }

    public void drawAlivePiece(Piece p, Canvas canvas) {
        RectF rect;
        if (flipped) {
            rect = new RectF(tileSideLength * p.col, verticalPadding + tileSideLength * (p.row + 1),
                    tileSideLength * (p.col + 1),verticalPadding + tileSideLength * p.row);
        }
        else {
            rect = new RectF(tileSideLength * p.col, verticalPadding + tileSideLength * p.row,
                    tileSideLength * (p.col + 1), verticalPadding + tileSideLength * (p.row + 1));
        }
        Bitmap b = BitmapFactory.decodeResource(getResources(), getImageId(p));
        canvas.drawBitmap(b, null, rect, paint);
    }

    public void drawDeadPieces(Player player, ArrayList<Piece> pieces, Canvas canvas) {
        int imageSideLength = (width / (11));
        int xScalar = 0;

        int firstImageYCoord;
        int secondImageYCoord;

        if (player == g.p0) {
            firstImageYCoord = height - verticalPadding / 2 - imageSideLength;
            secondImageYCoord = firstImageYCoord + imageSideLength;
        } else {
            secondImageYCoord = verticalPadding / 2 + imageSideLength;
            firstImageYCoord = secondImageYCoord - imageSideLength;
        }

        if (player == g.p1) {
            xScalar += 3 * verticalPadding/4;
        }
        else {
            xScalar += width - 3 * verticalPadding/4;
        }

        for (int i = 0; i < pieces.size(); i++) {
            int firstX;
            int secondX;
            if (player == g.p0) {
                firstX = xScalar - i * imageSideLength;
                secondX = xScalar - (i+1) * imageSideLength;
            }
            else {
                firstX = xScalar + i * imageSideLength;
                secondX = xScalar + (i+1) * imageSideLength;
            }

            RectF rect;
            if (flipped) {
                rect = new RectF(firstX, secondImageYCoord,
                        secondX, firstImageYCoord);
            }
            else {
                rect = new RectF(firstX, firstImageYCoord,
                        secondX, secondImageYCoord);
            }

            Bitmap b = BitmapFactory.decodeResource(getResources(), getImageId(pieces.get(i)));
            canvas.drawBitmap(b, null, rect, paint);
            if (i == 7) {
                if (player == g.p0) {
                    xScalar += imageSideLength * (8);
                    firstImageYCoord += imageSideLength;
                    secondImageYCoord += imageSideLength;
                } else {
                    xScalar -= imageSideLength * (8);
                    firstImageYCoord -= imageSideLength;
                    secondImageYCoord -= imageSideLength;
                }

            }
        }
    }

    public int getImageId(Piece p) {
        int imageId = 0;
        if (p.player.getId() == 0) {
            if (p.type.equals("Pawn"))
                imageId = R.drawable.pawn_0;
            else if (p.type.equals("Rook"))
                imageId = R.drawable.rook_0;
            else if (p.type.equals("Knight"))
                imageId = R.drawable.knight_0;
            else if (p.type.equals("Bishop"))
                imageId = R.drawable.bishop_0;
            else if (p.type.equals("Queen"))
                imageId = R.drawable.queen_0;
            else if (p.type.equals("King"))
                imageId = R.drawable.king_0;
        } else if (p.player.getId() == 1) {
            if (p.type.equals("Pawn"))
                imageId = R.drawable.pawn_1;
            else if (p.type.equals("Rook"))
                imageId = R.drawable.rook_1;
            else if (p.type.equals("Knight"))
                imageId = R.drawable.knight_1;
            else if (p.type.equals("Bishop"))
                imageId = R.drawable.bishop_1;
            else if (p.type.equals("Queen"))
                imageId = R.drawable.queen_1;
            else if (p.type.equals("King"))
                imageId = R.drawable.king_1;
        }
        return imageId;
    }

    public int getRow(float x, float y) {
        if (x >= 0 && x < width && y > verticalPadding && y < height - verticalPadding) {
            for (int r = 0; r < 9; r++) {
                if (y < verticalPadding + tileSideLength * r)
                    return r - 1; // r - 1 since we want the index
            }
        }
        return -1;
    }

    public int getCol(float x, float y) {
        if (x >= 0 && x < width && y > verticalPadding && y < height - verticalPadding) {
            for (int c = 0; c < 9; c++) {
                if (x < tileSideLength * c)
                    return c - 1; // c - 1 since we want the index
            }
        }
        return -1;
    }

    public void drawCheckeredTiles(Canvas canvas) {
        for (int r = 0; r < g.b.bHeight; r++) {
            for (int c = 0; c < g.b.bWidth; c++) {
                // Even addition tiles are one color, odd addition tiles are another
                if ((c + r) % 2 == 0) {
                    paint.setColor(Color.rgb(241, 216, 181));
                } else {
                    paint.setColor(Color.rgb(181, 135, 101));
                }
                RectF rect;
                rect = new RectF(tileSideLength * c, verticalPadding + tileSideLength * r,
                        tileSideLength * (c + 1), verticalPadding + tileSideLength * (r + 1));

                canvas.drawRect(rect, paint);
            }
        }
    }

    public void drawSelectedMoves(Canvas canvas) {
        int[][] selectedMoves = g.getSelectedMoves();
        if (selectedMoves == null)
            return;

        // color the tile of the piece
        paint.setColor(Color.BLUE);
        RectF rect = new RectF(tileSideLength * g.b.selected.col, verticalPadding + tileSideLength * g.b.selected.row,
                tileSideLength * (g.b.selected.col + 1), verticalPadding + tileSideLength * (g.b.selected.row + 1));
        canvas.drawRect(rect, paint);

        for (int r = 0; r < g.b.bHeight; r++) {
            for (int c = 0; c < g.b.bWidth; c++) {
                if (selectedMoves[r][c] == 1) {
                    paint.setColor(Color.rgb(70, 225, 106));
                    rect = new RectF(tileSideLength * c, verticalPadding + tileSideLength * r, tileSideLength * (c + 1), verticalPadding + tileSideLength * (r + 1));
                    canvas.drawRect(rect, paint);
                } else if (selectedMoves[r][c] == 2) {
                    paint.setColor(Color.rgb(222, 122, 122));
                    rect = new RectF(tileSideLength * c, verticalPadding + tileSideLength * r, tileSideLength * (c + 1), verticalPadding + tileSideLength * (r + 1));
                    canvas.drawRect(rect, paint);
                } else if (selectedMoves[r][c] == -1 || selectedMoves[r][c] == -2) {
                    paint.setColor(Color.CYAN);
                    rect = new RectF(tileSideLength * c, verticalPadding + tileSideLength * r, tileSideLength * (c + 1), verticalPadding + tileSideLength * (r + 1));
                    canvas.drawRect(rect, paint);
                }
            }
        }
    }
    public void drawSelectedPiece(Canvas canvas) {
        int[][] selectedMoves = g.getSelectedMoves();
        if (selectedMoves == null)
            return;

        // color the tile of the piece
        paint.setColor(Color.BLUE);
        RectF rect = new RectF(tileSideLength * g.b.selected.col, verticalPadding + tileSideLength * g.b.selected.row,
                tileSideLength * (g.b.selected.col + 1), verticalPadding + tileSideLength * (g.b.selected.row + 1));
        canvas.drawRect(rect, paint);
    }
    public void drawAvatar(Player player, Canvas canvas, boolean open, boolean victoryDance) {

        // Victory dance stuff
        if (victoryDance) {
            if (victoryXScalar + 3*verticalPadding/4 <= width && victoryXScalar >= 0) {
                if (victoryDanceRight) {
                    victoryXScalar += 0;
                }
                else {
                    victoryXScalar += 0;
                }
            }
            else {
                if (victoryXScalar + 3*verticalPadding/4 > width) {
                    victoryXScalar = victoryXScalar + 3 * verticalPadding / 4;
                    victoryDanceRight = false;
                }
                else if (victoryXScalar < 0) {
                    victoryXScalar = 0;
                    victoryDanceRight = true;
                }
            }

        }
        else {
            victoryXScalar = 0;
        }

        RectF rect;
        if (player.getId() == 1) {
            if (flipped) {
                rect = new RectF(victoryXScalar , verticalPadding,
                        3 * verticalPadding / 4 + victoryXScalar, verticalPadding/4);
            }
            else {
                rect = new RectF(3 * verticalPadding / 4 + victoryXScalar, verticalPadding/4,
                        victoryXScalar, verticalPadding);
            }
            canvas.drawRect(rect, paint);

            if (open)
                canvas.drawBitmap(openp1Avatar, null, rect, null);
            else
                canvas.drawBitmap(p1Avatar, null, rect, null);
        }
        else if (player.getId() == 0) {
            if (flipped) {
                rect = new RectF(width - (3 * verticalPadding / 4 + victoryXScalar), height - verticalPadding/4,
                        width - victoryXScalar, height - verticalPadding);
            }
            else {
                rect = new RectF(width - (3 * verticalPadding / 4 + victoryXScalar), height - verticalPadding,
                        width - victoryXScalar, height - verticalPadding/4);
            }
            canvas.drawRect(rect, paint);

            if (open)
                canvas.drawBitmap(openp0Avatar, null, rect, null);
            else
                canvas.drawBitmap(p0Avatar, null, rect, null);

        }
    }

    // getting bitmap from vector drawable function found at stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable
    // although the chess images converted to bitmap fine, we needed to use this function to convert our other images
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
