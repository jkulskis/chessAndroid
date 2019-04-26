package com.example.chess;

public class Tile extends Piece{
    public Tile(int row, int col) {
        super(row, col);
    }

    public int[][] getPossibleMoves(Board b, boolean killAll) {
        return null;
    }

    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return null;
    }

    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return null;
    }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return null;
    }
}
