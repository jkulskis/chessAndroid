package com.example.chess;

public class Rook extends Piece {

    public Rook(Player player, int row, int col) {
        super(player, "Rook", 5, row, col);
    }

    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) {
        for (int r = row + 1; r < b.bHeight; r++) {
            if (b.tiles[r][col].value == 0) {
                possibleMoves[r][col] = 1;
            }
            else if (b.tiles[r][col].value > 0) {
                if (b.tiles[r][col].player == player.getEnemy() || killAll)
                    possibleMoves[r][col] = 2;
                break;
            }
        }
        for (int r = row - 1; r >= 0; r--) {
            if (b.tiles[r][col].value == 0) {
                possibleMoves[r][col] = 1;
            }
            else if (b.tiles[r][col].value > 0) {
                if (b.tiles[r][col].player == player.getEnemy() || killAll)
                    possibleMoves[r][col] = 2;
                break;
            }
        }
        return possibleMoves;
    }


    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) {
        for (int c = col + 1; c < b.bWidth; c++) {
            if (b.tiles[row][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[row][c].value > 0) {
                if (b.tiles[row][c].player == player.getEnemy() || killAll)
                    possibleMoves[row][c] = 2;
                break;
            }
        }
        for (int c = col - 1; c >= 0; c--) {
            if (b.tiles[row][c].value == 0) {
                possibleMoves[row][c] = 1;
            }
            else if (b.tiles[row][c].value > 0) {
                if (b.tiles[row][c].player == player.getEnemy() || killAll)
                    possibleMoves[row][c] = 2;
                break;
            }
        }
        return possibleMoves;
    }

    // No diagonal moves for rook, so just return possibleMoves
    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }
}
