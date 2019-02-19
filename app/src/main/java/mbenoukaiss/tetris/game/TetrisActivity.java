package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import mbenoukaiss.tetris.R;

public class TetrisActivity extends Activity implements ScoreChangeListener {

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
            if(game.isStarted() && !game.isLost()) {
                if(!game.processFallingTetromino())
                    clock.setDelay(clock.getDelay() - 5);

                tetris.invalidateViews();
            } else if(game.isLost() && tetris.getAlpha() > 0.0f) {
                tetris.setAlpha(tetris.getAlpha() - 0.05f);
                clock.setDelay(10);
            } else if(game.isLost() && tetris.getAlpha() <= 0.0f) {
                Intent intent = new Intent(TetrisActivity.this, LostActivity.class);
                intent.putExtra("score", game.getScore());
                finish();
                startActivity(intent);
                return false;
            }

            return true;
        }, 750);
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
        game.setScoreListener(this);

        tetris = findViewById(R.id.gridview);
        tetris.setAdapter(new Adapter(this, game));
        tetris.setNumColumns(game.getGridSize().getWidth());
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        if(game.isLost()) return true;

        if(!game.isStarted()) {
            if(event.getActionMasked() == MotionEvent.ACTION_UP) {
                findViewById(R.id.controls_overlay).setVisibility(View.GONE);
                findViewById(R.id.gridview).setVisibility(View.VISIBLE);
                findViewById(R.id.score).setVisibility(View.VISIBLE);
                game.start();
                clock.start();
            }

            return true;
        }

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            swipeStart = new Point((int) event.getX(), (int) event.getY());

            if(SOFT_DROP_AREA.contains((int) event.getX(), (int) event.getY())) {
                clock.accelerate(Game.DEFAULT_SOFT_DROP_MODIFIER);
            }
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            if(clock.isAccelerated())
                clock.decelerate();

            if(event.getY() - swipeStart.y > MIN_SWIPE_DISTANCE &&
                    Math.abs(event.getX() - swipeStart.x) <= MAX_X_MARGIN_OF_ERROR) {
                game.hardDrop();
                tetris.invalidateViews();
                return true;
            }

            if(LEFT_AREA.contains((int) event.getX(), (int) event.getY()) && game.isTranslationValid(-1, 0)) {
                game.getFallingTetromino().left();
                tetris.invalidateViews();
            } else if(ROTATE_AREA.contains((int) event.getX(), (int) event.getY()) && game.isRotationValid()) {
                game.getFallingTetromino().rotate();
                tetris.invalidateViews();
            } else if(RIGHT_AREA.contains((int) event.getX(), (int) event.getY()) && game.isTranslationValid(1, 0)) {
                game.getFallingTetromino().right();
                tetris.invalidateViews();
            }
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(game.isStarted())
            clock.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(game.isStarted())
            clock.pause();
    }

    @Override
    public void onScoreChanged(int score) {
        TextView view = findViewById(R.id.score);
        view.setText(String.valueOf(score));
    }

}
