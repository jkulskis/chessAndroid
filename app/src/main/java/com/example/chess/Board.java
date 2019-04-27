package com.example.chess;

public class Board {
    int bHeight, bWidth;
    Piece[][] tiles;
    int[] score;
    Piece[] pieces;
    Piece selected;

    public Board(Player p0, Player p1) {
        // Standard chess board has width and height = 8 tiles
        this.bWidth = 8;
        this.bHeight = 8;

        tiles = new Piece[bHeight][bWidth];

        // initialize all tiles as 0 (empty) except for the starting position of pieces
        for (int r = 0; r < bHeight; r++) {
            for (int c = 0; c < bWidth; c++) {
                // create P0

                // P0 pieces

                // P0 Pawns
                if (r == 1) {
                    tiles[r][c] = new Pawn(p0, r, c);
                }
                // All Other P0 pieces
                else if (r == 0) {
                    // P0 Rooks
                    if (c == 0 || c == 7) {
                        tiles[r][c] = new Rook(p0, r, c);
                    }
                    // P0 Knights
                    if (c == 1 || c == 6) {
                        tiles[r][c] = new Knight(p0, r, c);
                    }
                    // P0 Bishops
                    if (c == 2 || c == 5) {
                        tiles[r][c] = new Bishop(p0, r, c);
                    }
                    // P0 Queen
                    if (c == 3) {
                        tiles[r][c] = new Queen(p0, r, c);
                    }
                    // P0 King
                    if (c == 4) {
                        tiles[r][c] = new King(p0, r, c);
                    }

                }

                // P1 pieces

                // Pawns
                else if (r == 6) {
                    tiles[r][c] = new Pawn(p1, r, c);
                    //tiles[r][c] = new Tile(r,c);
                }
                // All Other P1 pieces
                else if (r == 7) {
                    // P1 Rooks
                    if (c == 0 || c == 7) {
                        tiles[r][c] = new Rook(p1, r, c);
                    }
                    // P1 Knights
                    if (c == 1 || c == 6) {
                        //tiles[r][c] = new Tile(r, c);
                        tiles[r][c] = new Knight(p1, r, c);
                    }
                    // P1 Bishops
                    if (c == 2 || c == 5) {
                        //tiles[r][c] = new Tile(r, c);
                        tiles[r][c] = new Bishop(p1, r, c);
                    }
                    // P1 Queen
                    if (c == 3) {
                        //tiles[r][c] = new Tile(r, c);
                        tiles[r][c] = new Queen(p1, r, c);
                    }
                    // P1 King
                    if (c == 4) {
                        tiles[r][c] = new King(p1, r, c);
                    }
                }
                // Empty Tiles
                else {
                    tiles[r][c] = new Tile(r, c);
                }
                // Test Case
                if (r == 4 && c == 4) {
                    //tiles[r][c] = new King(p0, r, c);
                }
            }
        }
        // first index corresponds to score of player 1
        // second index corresponds to score of player 2
        score = new int[2];
        selected = null;
    }

    public String toString() {
        String s = "";

        for (int r = 0; r < bHeight; r++) {
            s += r + " |";

            for (int c = 0; c < bWidth; c++) {
                s += (int) tiles[r][c].value + "|";
            }
            s += "\n";
        }
        s += "---------------------------\n";
        s+= "    0 1 2 3 4 5 6 7";
        return s;
    }


}
