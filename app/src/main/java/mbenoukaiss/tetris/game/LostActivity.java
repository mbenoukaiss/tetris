package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.Score;
import mbenoukaiss.tetris.highscores.Scoreboard;

public class LostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int score = intent.getIntExtra("score", 0);

        Scoreboard dao = new Scoreboard(getApplicationContext());
        dao.open();
        dao.insertScore(new Score(username, score, DateFormat.getDateInstance().format(new Date())));

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(String.format(scoreView.getText().toString(), score));

        if(dao.highestScore() <= score) {
            TextView highestScore = findViewById(R.id.highest_score);
            highestScore.setText(R.string.highest_score);
        }

        dao.close();

        findViewById(R.id.main_menu).setOnClickListener(v -> finish());
    }

}
