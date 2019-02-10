package mbenoukaiss.tetris.pieces;

import android.graphics.Rect;

import mbenoukaiss.tetris.Movable;

public class Tetromino implements Movable {

    private Rect position;
    private int[][] matrix;
    private int color;

    public int getX() {
        return position.right;
    }

    public int getY() {
        return position.top;
    }

    public int getWidth() {
        return position.left - position.right;
    }

    public int getHeight() {
        return position.bottom - position.top;
    }

    private void setWidth(int width) {
        position.right = position.left + width;
    }

    private void setHeight(int height) {
        position.bottom = position.top + height;
    }

    @Override
    public void rotate() {
        setHeight(matrix[0].length);
        setWidth(matrix.length);

        int[][] newMatrix = new int[getWidth()][getHeight()];
        for(int i = 0; i < getWidth(); ++i) {
            for(int j = 0; j < getHeight(); ++j) {
                newMatrix[j][getWidth()-1-i] = matrix[i][j];
            }
        }

        matrix = newMatrix;
    }

    @Override
    public void left() {
        --position.left;
        --position.right;
    }

    @Override
    public void right() {
        ++position.left;
        ++position.right;
    }

    @Override
    public void down() {
        ++position.top;
    }
    
}
