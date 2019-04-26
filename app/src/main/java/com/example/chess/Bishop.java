package com.example.chess;

public class Bishop extends Piece {

    public Bishop(Player player, int row, int col) {
        super(player, "Bishop", 3, row, col);
    }

    // No vertical moves for bishop, so just return possibleMoves
    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }

    // No horizontal moves for bishop, so just return possibleMoves
    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) {
        // Up + Right Diagonal Moves
        int c = col + 1;
        int r = row + 1;

        while(c < b.bWidth && r < b.bHeight) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy() || killAll)
                    possibleMoves[r][c] = 2;
                break;
            }
            r++;
            c++;
        }

        // Up + Left Diagonal Moves
        c = col - 1;
        r = row + 1;

        while(c >= 0 && r < b.bHeight) {
            if (b.tiles[r][c].value == 0) {

                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy() || killAll)
                    possibleMoves[r][c] = 2;
                break;
            }
            r++;
            c--;
        }

        // Down + Right Diagonal Moves
        c = col + 1;
        r = row - 1;

        while(c < b.bWidth && r >= 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy() || killAll)
                    possibleMoves[r][c] = 2;
                break;
            }
            r--;
            c++;
        }

        // Down + Left Diagonal Moves
        c = col - 1;
        r = row - 1;

        while(c >= 0 && r >= 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy() || killAll)
                    possibleMoves[r][c] = 2;
                break;
            }
            r--;
            c--;
        }
        return possibleMoves;
    }
}
