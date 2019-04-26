package com.example.chess;

public abstract class Piece {
    double value; // using standard chess valuations
    int player;
    int row, col; // location of the piece
    String type;

    public Piece() {
        value = 0;
        player = -1;
        type = null;
        row = -1;
        col = -1;
    }
    public Piece(int row, int col) {
        this();
        this.row = row;
        this.col = col;
    }

    public Piece(int player, String type, double value, int row, int col) {
        this(row, col);
        this.player = player;
        this.type = type;
        this.value = value;
    }

    public void move(int vert, int horizontal) {
        row += vert;
        col += horizontal;
    }

    public abstract int[][] getPossibleMoves(Board b);

    public abstract int[][] get_vert_moves(Board b, int[][] possibleMoves);

    public abstract int[][] get_horiz_moves(Board b, int[][] possibleMoves);

    public abstract int[][] get_diag_moves(Board b, int[][] possibleMoves);

}