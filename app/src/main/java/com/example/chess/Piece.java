package com.example.chess;

public abstract class Piece {
    double value; // using standard chess valuations
    Player player;
    int row, col; // location of the piece
    String type;
    // Have to track moves for pawns, kings, and rooks since it affects
    // their possible moves...also, can use this for stats
    int numMoves;

    public Piece() {
        value = 0;
        player = new Player();
        type = null;
        row = -1;
        col = -1;
    }
    public Piece(int row, int col) {
        this();
        this.row = row;
        this.col = col;
    }

    public Piece(Player player, String type, double value, int row, int col) {
        this(row, col);
        this.numMoves = 0;
        this.player = player;
        this.type = type;
        this.value = value;
        player.addPiece(this);
    }

    public boolean move(Board b, int r, int c) {
        if (value == 0) {
            System.out.println("Error! Invalid Tile Selection");
            return false;
        }

        int[][] possibleMoves = getPossibleMoves(b, false, new int[b.bHeight][b.bWidth]);

        if (possibleMoves[r][c] == 0) {
            System.out.println("Error! Invalid Move");
            return false;
        }
        // check if castling, and move rook if it is
        if (possibleMoves[r][c] == -1) {
            b.tiles[row][0].move(b, row, 3);
        }
        else if (possibleMoves[r][c] == -2) {
            b.tiles[row][b.bWidth - 1].move(b, r, b.bWidth - 3);
        }
        if (b.tiles[r][c].value > 0) {
            player.killPiece(b.tiles[r][c]);
        }

        // set current tile to empty tile
        b.tiles[row][col] = new Tile(row, col);
        // move piece to new tile
        this.row = r;
        this.col = c;
        b.tiles[r][c] = this;
        // increase piece number of moves
        this.numMoves++;
        return true;
    }

    public String movesToString(Board b) {
        int[][] possibleMoves = getPossibleMoves(b, false, new int[b.bHeight][b.bWidth]);
        String s = "";

        for (int r = 0; r < b.bHeight; r++) {
            s += r + " |";

            for (int c = 0; c < b.bWidth; c++) {
                if (possibleMoves[r][c] != 1)
                    s += "#|";
                else
                    s += (int) b.tiles[r][c].value + "|";
            }
            s += "\n";
        }
        s += "---------------------------\n";
        s+= "   0 1 2 3 4 5 6 7";
        return s;
    }

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
        possibleMoves = get_diag_moves(b, get_horiz_moves(b, get_vert_moves(b, possibleMoves, false), false), false);
        // Only run through the king check if killAll is false, otherwise there will be an infinite loop
        if (!killAll) {
            for (int r = 0; r < b.bHeight; r++) {
                for (int c = 0; c < b.bWidth; c++) {
                    if (possibleMoves[r][c] != 0) {
                        Piece oldPiece = b.tiles[r][c];
                        b.tiles[r][c] = this;
                        if (player.findDangerZone(b)[kingRow][kingCol] != 0) {
                            possibleMoves[r][c] = 0;
                        }
                        b.tiles[r][c] = oldPiece;
                    }
                }
            }
        }

        return possibleMoves;
    }

    public int[][] getKillZone(Board b, int[][] possibleMoves) {
        return getPossibleMoves(b, true, possibleMoves);
    }

    public abstract int[][] get_vert_moves(Board b, int[][] possibleMoves, boolean killAll);

    public abstract int[][] get_horiz_moves(Board b, int[][] possibleMoves, boolean killAll);

    public abstract int[][] get_diag_moves(Board b, int[][] possibleMoves, boolean killAll);

}