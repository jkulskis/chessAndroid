package com.example.chess;

public class Pawn extends Piece {
    int moveAbility;

    public Pawn(Player player, int row, int col) {
        super(player, "Pawn", 1, row, col);

        moveAbility = 2;
    }

    // Override kill zone for pawn, since only diagonal moves are kill moves...
    // write the method again since we are not checking for enemies now
    @Override
    public int[][] getKillZone(Board b, int[][] possibleMoves) {
        return get_diag_moves(b, possibleMoves, true);
    }

    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) {
        // check to see if the pawn has moved yet, since this will change how far it can go
        if (numMoves > 0) {
            moveAbility = 1;
        }
        // Have to check player type since P0 pawns move up and P1 pawns move down
        if (player.getId() == 0) {
            for (int r = row + 1; r < b.bHeight && r < row + 1 + moveAbility; r++) {
                if (b.tiles[r][col].value == 0) {
                    possibleMoves[r][col] = 1;
                }
            }
        }
        else {
            for (int r = row - 1; r >= 0 && r > row - 1 - moveAbility; r--) {
                if (b.tiles[r][col].value == 0) {
                    possibleMoves[r][col] = 1;
                }
            }
        }
        return possibleMoves;
    }

    // No horizontal moves for pawn, so just return possibleMoves
    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }


    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) {
        // Only case of diagonal move is if an enemy is able to be captured...
        // Have to check player type since P0 pawns move up and P1 pawns move down
        if (player.getId() == 0) {
            if (row + 1 < b.bWidth && col + 1 < b.bHeight) {
                if (b.tiles[row + 1][col + 1].value > 0 || killAll)
                    if (b.tiles[row + 1][col + 1].player != player  || killAll)
                        possibleMoves[row + 1][col + 1] = 2;
            }
            if (row + 1 < b.bWidth && col - 1 >= 0) {
                if (b.tiles[row + 1][col - 1].value > 0 || killAll)
                    if (b.tiles[row + 1][col - 1].player != player  || killAll)
                        possibleMoves[row + 1][col - 1] = 2;
            }
        }
        else {
            if (row - 1 >= 0 && col + 1 < b.bHeight) {
                if (b.tiles[row - 1][col + 1].value > 0 || killAll)
                    if (b.tiles[row - 1][col + 1].player != player  || killAll)
                        possibleMoves[row - 1][col + 1] = 2;
            }
            if (row - 1 >= 0 && col - 1 >= 0) {
                if (b.tiles[row - 1][col - 1].value > 0 || killAll)
                    if (b.tiles[row - 1][col - 1].player != player  || killAll)
                        possibleMoves[row - 1][col - 1] = 2;
            }
        }
        return possibleMoves;
    }
}
