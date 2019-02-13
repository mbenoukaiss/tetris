package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.GridView;

import mbenoukaiss.tetris.R;

public class TetrisActivity extends Activity {

    private static final int MIN_SWIPE_DISTANCE = 75;

    private static final int MAX_X_MARGIN_OF_ERROR = 250;

    private Rect LEFT_AREA;

    private Rect ROTATE_AREA;

    private Rect SOFT_DROP_AREA;

    private Rect RIGHT_AREA;

    private Game game;

    private GridView tetris;

    private Point swipeStart;

    private Clock clock;

    public TetrisActivity() {
        swipeStart = new Point();

        clock = new Clock(() -> {
            if(!game.isLost()) {
                game.processFallingTetromino();
                tetris.invalidateViews();
            } else if(tetris.getAlpha() > 0.0f) {
                tetris.setAlpha(tetris.getAlpha() - 0.05f);
                clock.setDelay(50);
            } else {
                Intent intent = new Intent(TetrisActivity.this, LostActivity.class);
                startActivity(intent);
                return false;
            }

            return true;
        }, 500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tetris);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        LEFT_AREA = new Rect(0, 0, screenSize.x / 3, screenSize.y);
        ROTATE_AREA = new Rect(screenSize.x / 3 + 1, screenSize.y / 2 + 1, 2 * screenSize.x / 3 - 1, screenSize.y);
        SOFT_DROP_AREA = new Rect(screenSize.x / 3 + 1, 0, 2 * screenSize.x / 3 - 1, screenSize.y / 2);
        RIGHT_AREA = new Rect(2 * screenSize.x / 3, 0, screenSize.x, screenSize.y);

        game = new Game(getApplicationContext());

        tetris = findViewById(R.id.gridview);
        tetris.setAdapter(new Adapter(this, tetris, game));
        tetris.setNumColumns(game.getGridSize().getWidth());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!game.isStarted()) {
            if(event.getActionMasked() == MotionEvent.ACTION_UP) {
                game.start();
                tetris.invalidateViews();
                clock.run();
            }

            return true;
        }

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            swipeStart = new Point((int) event.getX(), (int) event.getY());

            if(SOFT_DROP_AREA.contains((int) event.getX(), (int) event.getY())) {
                clock.setDelay(50);
            }
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            clock.setDelay(500);

            if(event.getY() - swipeStart.y > MIN_SWIPE_DISTANCE &&
                    Math.abs(event.getX() - swipeStart.x) <= MAX_X_MARGIN_OF_ERROR) {
                game.hardDrop();
                tetris.invalidateViews();
                return true;
            }

            if(LEFT_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isTranslationValid(-1, 0)) {
                    game.getFallingTetromino().left();
                    tetris.invalidateViews();
                }
            } else if(ROTATE_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isRotationValid()) {
                    game.getFallingTetromino().rotate();
                    tetris.invalidateViews();
                }
            } else if(RIGHT_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isTranslationValid(1, 0)) {
                    game.getFallingTetromino().right();
                    tetris.invalidateViews();
                }
            }
        }

        return true;
    }

}
