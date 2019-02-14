package mbenoukaiss.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;

import mbenoukaiss.tetris.game.TetrisActivity;
import mbenoukaiss.tetris.highscores.ScoreboardActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        Button play = findViewById(R.id.play);
        play.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TetrisActivity.class);
            startActivity(intent);
        });

        Button highscores = findViewById(R.id.highscores);
        highscores.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
            startActivity(intent);
        });

        Button quit = findViewById(R.id.quit);
        quit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        try {
            openFileOutput("scores", MODE_PRIVATE).close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
