package com.example.chess;

public class King extends Piece {

    public King(Player player, int row, int col) {
        super(player, "King", 2, row, col);
    }

    // For the king, we also need to check if the moves are not in the "danger zone"...
    // the danger zone is any area that it is directly vulnerable to enemy pieces
    @Override
    public int[][] getPossibleMoves(Board b, boolean killAll, int[][] possibleMoves) {
        possibleMoves = get_perimeter_moves(b, possibleMoves);

        // remove any moves that are in the danger zone
        int[][] dangerZone = player.findDangerZone(b);
        for (int r = 0; r < b.bWidth; r++) {
            for (int c = 0; c < b.bHeight; c++) {
                if (dangerZone[r][c] == 1)
                    possibleMoves[r][c] = 0;
            }
        }
        possibleMoves = check_castling(b, possibleMoves);

        return possibleMoves;
    }

    // This method finds where the king can move if there is no danger zone
    // ...its purpose is to help find the danger zone for the enemy king
    // since the enemy king cannot move near the friend king
    @Override
    public int[][] getKillZone(Board b, int[][] possibleTiles) {
        for (int r = row - 1; r < row + 2; r++) {
            possibleTiles = checkBounds(b, possibleTiles, r, col, true);
            possibleTiles = checkBounds(b, possibleTiles, r, col - 1, true);
            possibleTiles = checkBounds(b, possibleTiles, r, col + 1, true);
        }
        return possibleTiles;
    }

    public int[][] checkBounds(Board b, int[][] possibleTiles, int r, int c, boolean killAll) {
        if(c < b.bWidth && r < b.bHeight && c >= 0 && r >= 0) {
            if (b.tiles[r][c].player != player || killAll)
                possibleTiles[r][c] = 1;
            if (b.tiles[r][c].player == player.getEnemy() || killAll)
                possibleTiles[r][c] = 2;
        }
        return possibleTiles;
    }

    public int[][] checkMoves(Board b, int[][] possibleMoves, int r, int c) {
        if(c < b.bWidth && r < b.bHeight && c >= 0 && r >= 0) {
            if (b.tiles[r][c].value == 0) {
                possibleMoves[r][c] = 1;
            }
            else if (b.tiles[r][c].value > 0) {
                if (b.tiles[r][c].player == player.getEnemy())
                    possibleMoves[r][c] = 2;
            }
        }
        return possibleMoves;
    }
    // Since the king's moves are in a square around it or another special case with
    // castling, it makes sense to not use the standard move finding methods
    public int[][] get_perimeter_moves(Board b, int[][] possibleMoves) {
        for (int r = row - 1; r < row + 2; r++) {
            if (r != row)
                possibleMoves = checkBounds(b, possibleMoves, r, col, false);
            possibleMoves = checkBounds(b, possibleMoves, r, col - 1, false);
            possibleMoves = checkBounds(b, possibleMoves, r, col + 1, false);
        }
        return possibleMoves;
    }

    public int[][] check_castling(Board b, int[][] possibleMoves) {
        // can't castle if numMoves > 0 or king is in check, so just return possibleMoves
        // if either of these are true
        if (numMoves > 0 || player.checked)
            return possibleMoves;

        // if non king/rooks un the row then castling impossible...it is
        // worth it to loop through the row to check, since it takes much more computation
        // to find the danger zone and go through all of the other checks
        boolean check1 = true;
        boolean check2 = true;
        for (int c = 0; c < col + 1; c++) {
            if (b.tiles[row][c].value != 0) {
                if (!b.tiles[row][c].type.equals("Rook") && !b.tiles[row][c].type.equals("King")) {
                    check1 = false;
                }
            }
        }
        for (int c = col; c < b.bWidth; c++) {
            if (b.tiles[row][c].value != 0) {
                if (!b.tiles[row][c].type.equals("Rook") && !b.tiles[row][c].type.equals("King")) {
                    check2 = false;
                }
            }
        }
        if (!check1 && !check2)
            return possibleMoves;

        Piece p1 = b.tiles[row][0];
        Piece p2 = b.tiles[row][b.bWidth - 1];

        int[][] dangerZone = player.findDangerZone(b);

        if (check1 && p1.value > 0 && p1.type.equals("Rook") && p1.numMoves == 0 && p1.player == player) {
            // if the above are true, check that there are no pieces between them,
            // and that the tiles between them (inclusive) are not in the danger zone
            for (int c = 1; c < col; c++) {
                if (b.tiles[row][c].value != 0 || dangerZone[row][c] != 0) {
                    break;
                }
                if (c == col - 1) {
                    // if castle 1 is available, set the king castle tile to -1
                    possibleMoves[row][col - 2] = -1;
                }
            }

        }
        if (check2 && p2.value > 0 && p2.type.equals("Rook") && p2.numMoves == 0 && p2.player == player) {
            // if the above are true, check that there are no pieces between them,
            // and that the tiles between them (inclusive) are not in the danger zone
            for (int c = col + 1; c < b.bWidth - 1; c++) {
                if (b.tiles[row][c].value != 0 || dangerZone[row][c] != 0) {
                    break;
                }
                if (c ==  b.bWidth - 2) {
                    // if castle 2 is available, set the king castle tile to -2
                    possibleMoves[row][col + 2] = -2;
                }
            }

        }
        return possibleMoves;
    }


    public int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return possibleMoves;
    }

    public int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return possibleMoves;
    }

    public int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll) {
        return possibleMoves;
    }
}
