package com.example.chess;

public class Board {
    int bHeight, bWidth;
    Piece[][] tiles;
    int[] score;
    Piece[] pieces;

    public Board() {
        // Standard chess board has width and height = 8 tiles
        this.bWidth = 8;
        this.bHeight = 8;

        tiles = new Piece[bHeight][bWidth];

        // initialize all tiles as 0 (empty) except for the starting position of pieces
        for (int r = 0; r < bHeight; r++) {
            for (int c = 0; c < bWidth; c++) {
                // P0 pieces

                // P0 Pawns
                if (r == 1) {
                    tiles[r][c] = new Pawn(0, r, c);
                }
                // All Other P0 pieces
                else if (r == 0) {
                    // P0 Rooks
                    if (c == 0 || c == 7) {
                        tiles[r][c] = new Rook(0, r, c);
                    }
                    // P0 Knights
                    if (c == 1 || c == 6) {
                        tiles[r][c] = new Knight(0, r, c);
                    }
                    // P0 Bishops
                    if (c == 2 || c == 5) {
                        tiles[r][c] = new Bishop(0, r, c);
                    }
                    // P0 com.example.chess.Queen
                    if (c == 3) {
                        tiles[r][c] = new Queen(0, r, c);
                    }
                    // P0 com.example.chess.King
                    if (c == 4) {
                        tiles[r][c] = new King(0, r, c);
                    }
                }

                // P1 pieces

                // Pawns
                else if (r == 6) {
                    tiles[r][c] = new Pawn(1, r, c);
                }
                // All Other P1 pieces
                else if (r == 7) {
                    // P1 Rooks
                    if (c == 0 || c == 7) {
                        tiles[r][c] = new Rook(0, r, c);
                    }
                    // P1 Knights
                    if (c == 1 || c == 6) {
                        tiles[r][c] = new Knight(0, r, c);
                    }
                    // P1 Bishops
                    if (c == 2 || c == 5) {
                        tiles[r][c] = new Bishop(0, r, c);
                    }
                    // P1 com.example.chess.Queen
                    if (c == 3) {
                        tiles[r][c] = new Queen(0, r, c);
                    }
                    // P1 com.example.chess.King
                    if (c == 4) {
                        tiles[r][c] = new King(0, r, c);
                    }
                }
                // Empty Tiles
                else {
                    tiles[r][c] = new Tile(r, c);
                }
            }
        }
        // first index corresponds to score of player 1
        // second index corresponds to score of player 2
        score = new int[2];
    }

    public String toString() {
        String s = "";

        for (int r = 0; r < bHeight; r++) {
            s += "|";

            for (int c = 0; c < bWidth; c++) {
                s += (int) tiles[r][c].value + "|";
            }
            s += "\n";
        }
        s += "\n------------------\n";
        s+= " 0 1 2 3 4 5 6 7 8";
        return s;
    }


}
