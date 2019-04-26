package com.example.chess;

public class Queen extends Piece {
    public Queen(int player, int row, int col) {
        super(player, "com.example.chess.Queen", 9, row, col);
    }

    public int[][] getPossibleMoves(Board b) {
        int[][] possibleMoves = new int[b.bHeight][b.bWidth];
        possibleMoves = get_vert_moves(b, possibleMoves);
        possibleMoves = get_horiz_moves(b, possibleMoves);
        possibleMoves = get_diag_moves(b, possibleMoves);
        return possibleMoves;
    }

    public int[][] get_vert_moves(Board b, int[][] possibleMoves) {
        for (int r = row + 1; r < b.bHeight; r++) {
            if (b.tiles[r][col].value == 0) {
                possibleMoves[r][col] = 1;
            }
            else if (b.tiles[r][col].value > 0) {
                possibleMoves[r][col] = 1;
                break;
            }
        }
        for (int r = row - 1; r >= 0; r--) {
            if (b.tiles[r][col].value == 0) {
                possibleMoves[r][col] = 1;
            }
            else if (b.tiles[r][col].value > 0) {
                possibleMoves[r][col] = 1;
                break;
            }
        }
        return possibleMoves;
    }

    public int[][] get_horiz_moves(Board b, int[][] possibleMoves) {
        for (int c = col + 1; c < b.bWidth; c++) {
            if (b.tiles[row][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[row][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }
        for (int c = col - 1; c < b.bWidth; c++) {
            if (b.tiles[row][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[row][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }
        return possibleMoves;
    }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves) {
        int c = b.bWidth + 1;
        int r = b.bHeight + 1;

        while(c < b.bWidth && r < b.bHeight) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }
        c = b.bWidth - 1;
        r = b.bHeight + 1;

        while(c > 0 && r < b.bHeight) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }

        c = b.bWidth + 1;
        r = b.bHeight - 1;

        while(c < b.bWidth && r > 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }

        c = b.bWidth - 1;
        r = b.bHeight - 1;

        while(c < 0 && r > 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                possibleMoves[row][c] = 1;
                break;
            }
        }
        return possibleMoves;
    }

    public String movesToString(Board b) {
        int[][] arrayToPrint = getPossibleMoves(b);
        String s = "";

        for (int r = 0; r < b.bHeight; r++) {
            s += "|";

            for (int c = 0; c < b.bWidth; c++) {
                s += (int) arrayToPrint[r][c] + "|";
            }
            s += "\n";
        }
        s += "\n------------------\n";
        s+= " 0 1 2 3 4 5 6 7 8";
        return s;
    }
}
