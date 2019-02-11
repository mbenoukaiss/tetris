package mbenoukaiss.tetris.pieces;

import android.graphics.Point;
import android.util.Size;

import mbenoukaiss.tetris.Movable;

public class Tetromino implements Movable {

    public enum Type {
        I(0xFF00FFFF, 4, 1),
        O(0xFFFFFF00, 2, 2),
        T(0xFF551A8B, 3, 2),
        L(0xFFFFA500, 3, 2),
        J(0xFF0000FF, 3, 2),
        Z(0xFFFF0000, 3, 2),
        S(0xFF00FF00, 3, 2);

        final int color;
        final int width;
        final int height;

        Type(int color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int[][] getLayout() {
            switch(this) {
                case I:
                    return new int[][]{
                            {1, 1, 1, 1}
                    };
                case O:
                    return new int[][]{
                            {1, 1},
                            {1, 1}
                    };
                case T:
                    return new int[][]{
                            {1, 1, 1},
                            {0, 1, 0}
                    };
                case L:
                    return new int[][]{
                            {1, 1, 1},
                            {1, 0, 0}
                    };
                case J:
                    return new int[][]{
                            {1, 1, 1},
                            {0, 0, 1}
                    };
                case Z:
                    return new int[][]{
                            {1, 1, 0},
                            {0, 1, 1}
                    };
                case S:
                    return new int[][]{
                            {0, 1, 1},
                            {1, 1, 0}
                    };
            }

            throw new RuntimeException("Tetromino type was added but is not handled");
        }

    }

    public Type type;
    private Point position;
    private Size size;
    private int[][] matrix;

    Tetromino(Type type, Point position) {
        this.type = type;
        this.position = position;
        this.size = new Size(type.width, type.height);
        this.matrix = type.getLayout();
    }

    public Point getPosition() {
        return new Point(position);
    }

    public Size getSize() {
        return size;
    }

    public int getColor() {
        return type.color;
    }

    public int[][] getLayout() {
        return matrix;
    }

    @Override
    public void rotate() {
        size = new Size(size.getHeight(), size.getWidth());

        int[][] newMatrix = new int[size.getHeight()][size.getWidth()];
        for(int i = 0; i < size.getWidth(); ++i) {
            for(int j = 0; j < size.getHeight(); ++j) {
                newMatrix[j][size.getWidth()-1-i] = matrix[i][j];
            }
        }

        matrix = newMatrix;
    }

    @Override
    public void left() {
        --position.x;
    }

    @Override
    public void right() {
        ++position.x;
    }

    @Override
    public void down() {
        ++position.y;
    }

}
