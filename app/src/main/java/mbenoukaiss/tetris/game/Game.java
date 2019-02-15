package mbenoukaiss.tetris.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.Size;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.game.pieces.Tetromino;
import mbenoukaiss.tetris.game.pieces.TetrominoFactory;

public class Game {

    public static final int DEFAULT_GRID_WIDTH = 12;

    public static final int DEFAULT_GRID_HEIGHT = 16;

    public static final int DEFAULT_SOFT_DROP_MODIFIER = 4;

    private ScoreChangeListener scoreListener;

    private Size gridSize;

    private TetrominoFactory factory;

    private Queue<Tetromino> future;

    private Tetromino falling;

    private Integer[][] fallen;

    private int score;

    private boolean started;

    private boolean lost;

    public Game(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String[] size = preferences.getString("size", Game.DEFAULT_GRID_WIDTH + " x " + Game.DEFAULT_GRID_HEIGHT)
                .split(" x ");

        this.gridSize = new Size(Integer.valueOf(size[0]), Integer.valueOf(size[1]));
        this.factory = new TetrominoFactory(gridSize.getWidth());
        this.future = new LinkedList<>();
        this.fallen = new Integer[gridSize.getWidth()][gridSize.getHeight()];
        this.score = 0;
        this.started = false;
        this.lost = false;
    }

    public Size getGridSize() {
        return gridSize;
    }

    public void setScoreListener(ScoreChangeListener scoreListener) {
        this.scoreListener = scoreListener;
        this.scoreListener.onScoreChanged(score);
    }

    public Tetromino nextTetromino() {
        future.add(factory.generate());
        return future.remove();
    }

    public Iterator<Tetromino> futureTetrominoes() {
        return future.iterator();
    }

    public Tetromino getFallingTetromino() {
        return falling;
    }

    public int getSquareAt(Point position) {
        if(started && position.x >= falling.getPosition().x && position.y >= falling.getPosition().y &&
                position.x < falling.getPosition().x + falling.getSize().getWidth() &&
                position.y < falling.getPosition().y + falling.getSize().getHeight()) {

            if(falling.getLayout()[position.y - falling.getPosition().y][position.x - falling.getPosition().x] == 1)
                return falling.getColor();
        }

        Integer squareColor = fallen[position.x][position.y];

        return squareColor == null ? 0xFFFFFFFF : squareColor;
    }

    public boolean processFallingTetromino() {
        if(!isTranslationValid(0, 1)) { //reached bottom
            for(int i = 0; i < falling.getSize().getWidth(); ++i) {
                for(int j = 0; j < falling.getSize().getHeight(); ++j) {
                    if(falling.getLayout()[j][i] == 1) {
                        if(fallen[falling.getPosition().x + i][falling.getPosition().y + j] != null) {
                            lost();
                            return false;
                        }

                        fallen[falling.getPosition().x + i][falling.getPosition().y + j] = falling.getColor();
                    }
                }
            }

            calculateScore(checkLines());
            falling = nextTetromino();
            return false;
        } else {
            falling.down();
            return true;
        }
    }

    public void hardDrop() {
        while(isTranslationValid(0, 1)) {
            processFallingTetromino();
            ++score;
        }

        scoreListener.onScoreChanged(score);
    }

    public boolean isRotationValid() {
        if(falling.getPosition().x + falling.getSize().getHeight() > gridSize.getWidth() ||
                falling.getPosition().y + falling.getSize().getWidth() >= gridSize.getHeight())
            return false; //outside of the grid

        Tetromino clone = new Tetromino(falling);
        clone.rotate();

        for(int i = 0; i < clone.getSize().getWidth(); ++i) {
            for(int j = 0; j < clone.getSize().getHeight(); ++j) {
                if(clone.getLayout()[j][i] == 1 &&
                        fallen[clone.getPosition().x + i][clone.getPosition().y + j] != null)
                    return false;
            }
        }

        return true;
    }

    public boolean isTranslationValid(int ox, int oy) {
        if(Math.abs(ox) > 1 || oy < 0 || oy > 1)
            throw new IllegalArgumentException("Offset coordinate x must be either 1, 0 or -1 and y must be 0 or 1");

        boolean canTranslate = true;

        if(ox != 0) {
            canTranslate =  falling.getPosition().x + ox >= 0 &&
                    falling.getPosition().x + falling.getSize().getWidth() + ox <= gridSize.getWidth();

            for(int i = 0; i < falling.getSize().getHeight() && canTranslate; ++i) {
                int j = ox == -1 ? 0 : falling.getSize().getWidth() - 1;
                while(falling.getLayout()[i][j] == 0) j -= ox;

                if(fallen[falling.getPosition().x + j + ox][falling.getPosition().y + i] != null)
                    canTranslate = false;
            }
        }

        if(oy != 0) {
            canTranslate = falling.getPosition().y + falling.getSize().getHeight() < gridSize.getHeight();

            for(int i = 0; i < falling.getSize().getWidth() && canTranslate; ++i) {
                int j = falling.getSize().getHeight() - 1;
                while(falling.getLayout()[j][i] == 0) --j;

                if(fallen[falling.getPosition().x + i][falling.getPosition().y + j + 1] != null)
                    canTranslate = false;
            }
        }

        return canTranslate;
    }

    private int checkLines() {
        int count = 0;

        for(int i = 0; i < gridSize.getHeight(); ++i) {
            int j = 0;

            while(j < gridSize.getWidth())
                if(fallen[j][i] == null) break;
                else ++j;

            if(j == gridSize.getWidth()) {//complete line
                shiftFallen(i);
                ++count;
            }
        }

        return count;
    }

    private void shiftFallen(int row) {
        for(int i = row; i > 0; --i) {
            boolean allNull = true;

            for(int j = 0; j < gridSize.getWidth(); ++j) {
                fallen[j][i] = fallen[j][i - 1];

                if(fallen[j][i - 1] != null)
                    allNull = false;
            }

            if(allNull) return;
        }
    }

    private void calculateScore(int lines) {
        switch(lines) {
            case 0: return;
            case 1:
                score += 40;
                break;
            case 2:
                score += 100;
                break;
            case 3:
                score += 300;
                break;
            case 4:
                score += 1200;
                break;
            default:
                throw new IllegalArgumentException("Invalid lines count " + lines);
        }

        scoreListener.onScoreChanged(score);
    }

    public int getScore() {
        return score;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;

        for(int i = 0; i < 3; ++i)
            future.add(factory.generate());

        falling = nextTetromino();
    }

    public boolean isLost() {
        return lost;
    }

    private void lost() {
        lost = true;
        started = false;
        falling = null;
    }

}
