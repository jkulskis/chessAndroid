package com.example.chess;

public class Pawn extends Piece {

    public Pawn(int player, int row, int col) {
        super(player, "com.example.chess.Pawn", 1, row, col);
    }

    public int[][] getPossibleMoves(Board b) {
        return null;
    }

    public int[][] get_vert_moves(Board b, int[][] possibleMoves) {
        return new int[1][1];
    }

    public int[][] get_horiz_moves(Board b, int[][] possibleMoves) {
        return new int[1][1];
    }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves) {
        return new int[1][1];
    }
}
