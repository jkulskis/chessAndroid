package com.example.chess;

public class Game {
    Player p0;
    Player p1;
    Board b;
    Player currentPlayer;

    public Game(int p0Id, int p1Id, int openp0Id, int openp1Id) {
        p0 = new Player(0, p0Id, openp0Id);
        p1 = new Player(1, p1Id, openp1Id, p0);
        b = new Board(p0, p1);
        currentPlayer = p1;
    }

    public Game(Board b, Player p0, Player p1) {
        this.b = b;
        this.p0 = p0;
        this.p1 = p1;
    }

    public void startGame() {
        p0 = new Player(0, p0.avatarId, p0.openAvatarId);
        p1 = new Player(1, p1.avatarId, p1.openAvatarId, p0);
        b = new Board(p0, p1);
        currentPlayer = p1;
    }

    // returns 0 if the game is still going, 1 if it is over (player checkmated), and
    // 2 if only checked but not checkmated
    public int goToNextTurn() {
        b.selected = null;
        if (currentPlayer == p0) {
            currentPlayer = p1;
            if (p1.statusCheck(b)) {
                if (p1.checkMated(b)) {
                    System.out.println("P1 Checkmated!");
                    return 1;
                }
                System.out.println("P1 Checked!");
                return 2;
            }
            return 0;
        }
        currentPlayer = p0;
        if (p0.statusCheck(b)) {
            if (p0.checkMated(b)) {
                System.out.println("P0 Checkmated!");
                return 1;
            }
            System.out.println("P0 Checked!");
            return 2;
        }
        return 0;
    }

    // return -1 is the turn didn't work out, otherwise return goToNextTurn's value
    public int doTurn(int r1, int c1, int r2, int c2) {
        if (currentPlayer.move(b, r1, c1, r2, c2)) {
            // if was checked and moved, should be unchecked
            currentPlayer.checked = false;
            if (r2 == 0 || r2 == 7) {
                int pUpgrade = checkPawnUpgrade(r2, c2);
                if (pUpgrade != -1) {
                    return pUpgrade;
                }
            }
            goToNextTurn();
            return -1;
        }
        return -1;
    }

    public String getMoves(int r, int c) {
        if (r >= 0 && r < b.bHeight && c >= 0 && c < b.bWidth) {
            if (b.tiles[r][c].value > 0 && b.tiles[r][c].player == currentPlayer) {
                return b.tiles[r][c].movesToString(b);
            }
        }
        return b.toString();
    }

    public int[][] getSelectedMoves() {
        if (b.selected == null)
            return null;
        return b.selected.getPossibleMoves(b, false, new int[b.bHeight][b.bWidth]);
    }

    // check to see if the click is a valid move for the selected piece
    public boolean checkSelectMove(int row, int col) {
        if (row == -1 || b.selected == null)
            return false;
        int[][] possibleMoves = getSelectedMoves();
        if (possibleMoves[row][col] != 0)
            return true;
        return false;
    }
    public int checkPawnUpgrade(int r, int c) {
        if (b.tiles[r][c].type.equals("Pawn")) {
            return b.tiles[r][c].player.getId();
        }
        return -1;
    }
}
