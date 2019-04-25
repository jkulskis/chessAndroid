

public class Board {
    int bHeight, bWidth;
    int[][] tiles;
    int[] score;

    public Board() {
        // Standard chess board has width and height = 8 tiles
        this.bWidth = 8;
        this.bHeight = 8;

        tiles = new int[bHeight][bWidth];

        // initialize all tiles as 0 (empty) except for the starting position of pieces
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = 0;
            }
        }
        // first index corresponds to score of player 1
        // second index corresponds to score of player 2
        score = new int[2];
    }


}
