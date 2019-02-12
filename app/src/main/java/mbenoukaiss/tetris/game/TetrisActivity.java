package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.game.pieces.Tetromino;

public class TetrisActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tetris);

        Game game = new Game(getApplicationContext());

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new Adapter(this, gridview, game));
        gridview.setNumColumns(game.getGridSize().getWidth());

        Button left = findViewById(R.id.left);
        left.setOnClickListener(v -> {
            if(game.getFallingTetromino().getPosition().x > 0)
                game.getFallingTetromino().left();
        });

        Button rotate = findViewById(R.id.rotate);
        rotate.setOnClickListener(v -> {
            if(game.isRotationValid())
                game.getFallingTetromino().rotate();
        });

        Button right = findViewById(R.id.right);
        right.setOnClickListener(v -> {
            Tetromino t = game.getFallingTetromino();

            if(t.getPosition().x + t.getSize().getWidth() < game.getGridSize().getWidth())
                game.getFallingTetromino().right();
        });

    }

}
