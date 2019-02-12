package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.GridView;

import mbenoukaiss.tetris.R;

public class TetrisActivity extends Activity {

    private static final int MIN_SWIPE_DISTANCE = 75;

    private static final int MAX_X_MARGIN_OF_ERROR = 250;

    private Rect LEFT_TOUCH_AREA;

    private Rect ROTATE_TOUCH_AREA;

    private Rect RIGHT_TOUCH_AREA;

    private Game game;

    private GridView tetris;

    private Point swipeStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tetris);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        LEFT_TOUCH_AREA = new Rect(0, 0, screenSize.x / 3, screenSize.y);
        ROTATE_TOUCH_AREA = new Rect(screenSize.x / 3 + 1, 0, 2 * screenSize.x / 3 - 1, screenSize.y);
        RIGHT_TOUCH_AREA = new Rect(2 * screenSize.x / 3, 0, screenSize.x, screenSize.y);

        game = new Game(getApplicationContext());

        tetris = findViewById(R.id.gridview);
        tetris.setAdapter(new Adapter(this, tetris, game));
        tetris.setNumColumns(game.getGridSize().getWidth());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            swipeStart = new Point((int) event.getX(), (int) event.getY());
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            if(event.getY() - swipeStart.y > MIN_SWIPE_DISTANCE &&
                    Math.abs(event.getX() - swipeStart.x) <= MAX_X_MARGIN_OF_ERROR) {
                game.dropFallingTetromino();
                tetris.invalidateViews();
                return true;
            }

            if(LEFT_TOUCH_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isTranslationValid(-1, 0)) {
                    game.getFallingTetromino().left();
                    tetris.invalidateViews();
                }
            } else if(ROTATE_TOUCH_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isRotationValid()) {
                    game.getFallingTetromino().rotate();
                    tetris.invalidateViews();
                }
            } else if(RIGHT_TOUCH_AREA.contains((int) event.getX(), (int) event.getY())) {
                if(game.isTranslationValid(1, 0)) {
                    game.getFallingTetromino().right();
                    tetris.invalidateViews();
                }
            }
        }

        return true;
    }

}
