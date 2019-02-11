package mbenoukaiss.tetris;

import android.content.Context;
import android.graphics.Point;
import android.util.Size;

import java.util.Iterator;
import java.util.Stack;

import mbenoukaiss.tetris.pieces.Tetromino;
import mbenoukaiss.tetris.pieces.TetrominoFactory;
import mbenoukaiss.tetris.pieces.SquareView;

public class Game {

    private final Context context;

    private Size gridSize;

    private TetrominoFactory factory;

    private Stack<Tetromino> future;

    private Tetromino falling;

    private Integer[][] fallen;


    public Game(Context context) {
        this.context = context;
        this.gridSize = new Size(12, 16);
        this.factory = new TetrominoFactory(gridSize.getWidth());
        this.future = new Stack<>();
        this.fallen = new Integer[gridSize.getWidth()][gridSize.getHeight()];

        for(int i = 0; i < 3; ++i)
            future.add(factory.generate());

        falling = factory.generate();
    }

    public Size getGridSize() {
        return gridSize;
    }

    public Tetromino nextTetromino() {
        future.push(factory.generate());
        return future.pop();
    }

    public Iterator<Tetromino> futureTetrominoes() {
        return future.iterator();
    }

    public Tetromino getFallingTetromino() {
        return falling;
    }

    public SquareView getSquareAt(Point position) {
        if(position.x >= falling.getPosition().x && position.y >= falling.getPosition().y &&
                position.x < falling.getPosition().x + falling.getSize().getWidth() &&
                position.y < falling.getPosition().y + falling.getSize().getHeight()) {

            if(falling.getLayout()[position.y - falling.getPosition().y][position.x - falling.getPosition().x] == 1)
                return new SquareView(context, falling.getColor());
        }

        Integer squareColor = fallen[position.x][position.y];

        if(squareColor == null)
            return new SquareView(context, 0xFFFFFFFF);
        else
            return new SquareView(context, squareColor);
    }

    public void processFallingTetromino() {
        falling.down();

        boolean reachedBottom = falling.getPosition().y + falling.getSize().getHeight() == gridSize.getHeight();

        for(int i = 0; i < falling.getSize().getWidth() && !reachedBottom; ++i) {
            int j = falling.getSize().getHeight() - 1;
            System.out.println(i + "");
            while(falling.getLayout()[j][i] == 0) --j;

            if(fallen[falling.getPosition().x + i][falling.getPosition().y + j + 1] != null)
                reachedBottom = true;
        }

        if(reachedBottom) {
            for(int i = 0; i < falling.getSize().getWidth(); ++i) {
                for(int j = 0; j < falling.getSize().getHeight(); ++j) {
                    if(falling.getLayout()[j][i] == 1)
                        fallen[falling.getPosition().x + i][falling.getPosition().y + j] = falling.getColor();
                }
            }

            falling = nextTetromino();
        }
    }

    private void checkLines() {
        for(int i = 0; i < gridSize.getHeight(); ++i) {
            int j = 0;

            while(j < gridSize.getWidth())
                if(fallen[i][j++] == null) break;

            if(j == gridSize.getWidth()) //complete line
                shiftFallen(i);
        }
    }

    private void shiftFallen(int row) {
        for(int i = row; i < gridSize.getHeight() - 1; ++i) {
            boolean allNull = true;

            for(int j = 0; j < gridSize.getWidth(); ++j) {
                fallen[i][j] = fallen[i + 1][j];

                if(fallen[i + 1][j] != null)
                    allNull = false;
            }

            if(allNull) return;
        }
    }

}
