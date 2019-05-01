package com.example.chess;

import java.util.ArrayList;

public class Player {
    private int id;
    public int avatarId, openAvatarId;
    private Player enemy;
    ArrayList<Piece> alivePieces;
    ArrayList<Piece> deadPieces;
    boolean checked, checkmated; // true if the king is in check

    Player() {
        id = -1;
    }

    Player(int id) {
        this.id = id;
        alivePieces = new ArrayList<Piece>();
    }

    Player(int id, int avatarId, int openAvatarId) {
        this.id = id;
        this.avatarId = avatarId;
        this.openAvatarId = openAvatarId;
        alivePieces = new ArrayList<Piece>();
        deadPieces = new ArrayList<Piece>();
    }

    Player(int id, int avatarId, int openAvatarId, Player enemy) {
        this(id, 0, 0);
        this.avatarId = avatarId;
        this.openAvatarId = openAvatarId;
        this.enemy = enemy;
        enemy.setEnemy(this);
    }

    public void addPiece(Piece p) {
        alivePieces.add(p);
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void killPiece(Piece p) {
        for (int i = 0; i < enemy.alivePieces.size(); i++) {
            if (p == enemy.alivePieces.get(i)) {
                enemy.deadPieces.add(p);
                enemy.alivePieces.remove(i);
                return;
            }
        }
    }

    // Check to see if player is checked
    public boolean statusCheck(Board b) {
        // find where the king is
        int kingRow = 0;
        int kingCol = 0;
        for (int i = 0; i < alivePieces.size(); i++) {
            if (alivePieces.get(i).type.equals("King")) {
                kingRow = alivePieces.get(i).row;
                kingCol = alivePieces.get(i).col;
            }
        }

        int[][] dangerZone = findDangerZone(b);

        System.out.println(dangerZoneToString(b));

        if (dangerZone[kingRow][kingCol] != 0) {
            checked = true;
            return true;
        }
        checked = false;
        return false;

    }

    // Useful for determining king movement / checkmates
    public int[][] findDangerZone(Board b) {
        int[][] dangerZone = new int[b.bHeight][b.bWidth];

        for (int r = 0; r < b.bHeight; r++) {
            for (int c = 0; c < b.bWidth; c++) {
                if (b.tiles[r][c].player == this.enemy)
                    dangerZone = b.tiles[r][c].getKillZone(b, dangerZone);
            }
        }

        return dangerZone;
    }

    // If can't move anywhere ----> is checkmated
    public boolean checkMated(Board b) {
        int[][] allPossibleMoves = new int[b.bHeight][b.bWidth];

        for (int i = 0; i < alivePieces.size(); i++) {
            allPossibleMoves = alivePieces.get(i).getPossibleMoves(b, false, allPossibleMoves);
            for (int r = 0; r < b.bHeight; r++) {
                for (int c = 0; c < b.bHeight; c++){
                    if (allPossibleMoves[r][c] != 0)
                        return false;
                }
            }
        }
        int[][] arrayToPrint = allPossibleMoves;
        String s = "";

        for (int r = 0; r < b.bHeight; r++) {
            s += r + " |";

            for (int c = 0; c < b.bWidth; c++) {
                s += (int) arrayToPrint[r][c] + "|";
            }
            s += "\n";
        }
        s += "---------------------------\n";
        s+= "   0 1 2 3 4 5 6 7";
        System.out.println(s);
        // If got all the way here, must mean that no possible moves
        // so checkmate is true
        checkmated = true;
        return true;
    }


    public int getId() {
        return id;
    }

    public boolean move(Board b, int r1, int c1, int r2, int c2) {
        if (b.tiles[r1][c1].player != this) {
            System.out.println("Invalid Selection");
            return false;
        }
        return b.tiles[r1][c1].move(b, r2, c2);
    }

    public String stringAlivePieces() {
        String s = "";
        for (int i = 0; i < alivePieces.size(); i++) {
            s += alivePieces.get(i).type + ", ";
        }
        s = s.substring(0,s.length() - 2);
        return s;
    }

    public String dangerZoneToString(Board b) {
        int[][] arrayToPrint = findDangerZone(b);
        String s = "";

        for (int r = 0; r < b.bHeight; r++) {
            s += r + " |";

            for (int c = 0; c < b.bWidth; c++) {
                s += (int) arrayToPrint[r][c] + "|";
            }
            s += "\n";
        }
        s += "---------------------------\n";
        s+= "   0 1 2 3 4 5 6 7";
        return s;
    }
}
