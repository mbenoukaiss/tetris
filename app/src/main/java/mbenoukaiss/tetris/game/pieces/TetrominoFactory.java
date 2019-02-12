package mbenoukaiss.tetris.game.pieces;

import android.graphics.Point;

import java.util.HashMap;
import java.util.Map;

import static mbenoukaiss.tetris.game.pieces.Tetromino.Type;

public class TetrominoFactory {

    /**
     * Probabilities used to generate "random"
     * tetrominoes.
     */
    private final Map<Type, Double> probabilities;

    /**
     * The total of the values in probabilities map.
     */
    private double total;

    /**
     * The width of the grid on which the
     * tetrominoes will be generated.
     */
    private int gridWidth;

    /**
     * Default constructor. All tetrominoes will
     * have the same chance to get generated.
     */
    public TetrominoFactory(int gridWidth) {
        this.probabilities = new HashMap<>();
        probabilities.put(Type.I, 1.0d);
        probabilities.put(Type.O, 1.0d);
        probabilities.put(Type.T, 1.0d);
        probabilities.put(Type.L, 1.0d);
        probabilities.put(Type.J, 1.0d);
        probabilities.put(Type.Z, 1.0d);
        probabilities.put(Type.S, 1.0d);

        this.total = Type.values().length;
        this.gridWidth = gridWidth;
    }

    /**
     * Create a factory which will generate
     * tetrominoes using the defined probabilities
     *
     * @param probabilities Probability of each tetromino
     */
    public TetrominoFactory(Map<Type, Double> probabilities, int gridWidth) {
        this.probabilities = probabilities;
        this.total = 0;
        this.gridWidth = gridWidth;

        for(double probability : probabilities.values())
            total += probability;
    }

    /**
     * Generates a tetromino based on the given
     * probabilities.
     *
     * @return A tetromino
     */
    public mbenoukaiss.tetris.game.pieces.Tetromino generate() {
        Type type = null;

        do {
            double result = Math.random() * total;
            double sum = 0;

            for(Map.Entry<Type, Double> entry : probabilities.entrySet()) {
                sum += entry.getValue();

                if(sum >= result) {
                    type = entry.getKey();
                    break;
                }
            }
        } while(type == null);

        Point position = new Point(0, 0);
        position.x = (int) (Math.random() * (gridWidth - type.width + 1));

        return new mbenoukaiss.tetris.game.pieces.Tetromino(type, position);
    }

}
