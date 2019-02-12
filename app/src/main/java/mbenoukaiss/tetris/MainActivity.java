package mbenoukaiss.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import mbenoukaiss.tetris.game.TetrisActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button play = findViewById(R.id.play);
        play.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TetrisActivity.class);
            startActivity(intent);
        });

        Button highscores = findViewById(R.id.highscores);

        Button quit = findViewById(R.id.quit);
        quit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
