package com.example.chess;

public class Knight extends Piece {

    public Knight(Player player, int row, int col) {
        super(player, "Knight", 4, row, col);
    }

    @Override
    public int[][] getPossibleMoves(Board b, boolean killAll, int[][] possibleMoves) {
        // If player is checked, then we need to check if there are any "block" tiles
        // to block the check

        // find where the king is
        int kingRow = 0;
        int kingCol = 0;
        for (int i = 0; i < player.alivePieces.size(); i++) {
            if (player.alivePieces.get(i).type.equals("King")) {
                kingRow = player.alivePieces.get(i).row;
                kingCol = player.alivePieces.get(i).col;
            }
        }
        possibleMoves = get_L_moves(b, possibleMoves, killAll);

        if (!killAll) {
            for (int r = 0; r < b.bHeight; r++) {
                for (int c = 0; c < b.bWidth; c++) {
                    if (possibleMoves[r][c] != 0) {
                        Piece oldPiece = b.tiles[r][c];
                        b.tiles[r][c] = this;
                        b.tiles[row][col] = new Tile(r, c);
                        if (player.findDangerZone(b)[kingRow][kingCol] != 0) {
                            possibleMoves[r][c] = 0;
                        }
                        b.tiles[row][col] = this;
                        b.tiles[r][c] = oldPiece;
                    }
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public int[][] getKillZone(Board b, int[][] possibleMoves) {
        return get_L_moves(b, possibleMoves, true);
    }

    // Knight is special, so we will make a new method for finding its moves...
    // The available moves of a knight are "L" shaped, and when mapped out
    // they all lie on the perimeter of a 5 x 5 square centered around the piece.
    // For a perimeter tile to be a valid move, r != row, c != col, and c != r
    // therefore, just go 2 rows up and 2 rows down, and check col +- 2 when
    // r = row +- 1, and check col +- 1 when r = row +- 2
    public int[][] get_L_moves(Board b, int[][] possibleMoves, boolean killAll) {

        // Check moves to the right
        for (int r = row + 1; r < row + 3 && row < b.bWidth; r++) {
            if (r == row + 1) {
                possibleMoves = checkMove(b, possibleMoves, r, col + 2, killAll);
                possibleMoves = checkMove(b, possibleMoves, r, col - 2, killAll);
            }
            else if (r == row + 2) {
                possibleMoves = checkMove(b, possibleMoves, r, col + 1, killAll);
                possibleMoves = checkMove(b, possibleMoves, r, col - 1, killAll);
            }
        }
        // Check moves to the left
        for (int r = row - 1; r > row - 3 && row >= 0; r--) {
            if (r == row - 1) {
                possibleMoves = checkMove(b, possibleMoves, r, col + 2, killAll);
                possibleMoves = checkMove(b, possibleMoves, r, col - 2, killAll);
            }
            else if (r == row - 2) {
                possibleMoves = checkMove(b, possibleMoves, r, col + 1, killAll);
                possibleMoves = checkMove(b, possibleMoves, r, col - 1, killAll);
            }
        }

        return possibleMoves;
}

    public int[][] checkMove(Board b, int[][] possibleMoves, int r, int c, boolean killAll) {
        if(c < b.bWidth && r < b.bHeight && c >= 0 && r >= 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy() || killAll)
                    possibleMoves[r][c] = 2;
            }
        }
        return possibleMoves;
    }


    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }

    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) { return possibleMoves; }
}
