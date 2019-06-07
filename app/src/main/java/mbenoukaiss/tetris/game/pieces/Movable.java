package mbenoukaiss.tetris.game.pieces;

public interface Movable {

    /**
     * Applies clockwise rotation to the piece.
     */
    void rotate();

    /**
     * Moves the piece to the left.
     */
    void left();

    /**
     * Moves the piece to the right.
     */
    void right();

    /**
     * Moves the piece down.
     */
    void down();

}
