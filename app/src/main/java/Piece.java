

public class Piece {
    int value; // using standard chess valuations
    int player;
    String type;

    public Piece(String type, int player) {
        if (type == "pawn") {
            value = 1;
        }
        else if (type == "knight") {
            value = 3;
        }
        // redundant, but keeping in case want to change values easily later
        else if (type == "bishop") {
            value = 3;
        }
        else if (type == "rook") {
            value = 5;
        }
        else if (type == "queen") {
            value = 9;
        }
    }
}
