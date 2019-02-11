package mbenoukaiss.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import mbenoukaiss.tetris.pieces.Tetromino;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Game game = new Game(getApplicationContext());

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new Adapter(this, gridview, game));
        gridview.setHorizontalSpacing(0);
        gridview.setVerticalSpacing(0);
        gridview.setNumColumns(10);
        gridview.setNumColumns(game.getGridSize().getWidth());

        Button left = findViewById(R.id.left);
        left.setOnClickListener(v -> {
            if(game.getFallingTetromino().getPosition().x > 0)
                game.getFallingTetromino().left();
        });

        Button rotate = findViewById(R.id.rotate);
        rotate.setOnClickListener(v -> game.getFallingTetromino().rotate());

        Button right = findViewById(R.id.right);
        right.setOnClickListener(v -> {
            Tetromino t = game.getFallingTetromino();

            if(t.getPosition().x + t.getSize().getWidth() < game.getGridSize().getWidth())
                game.getFallingTetromino().right();
        });

    }
}
