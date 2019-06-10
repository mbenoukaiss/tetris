package mbenoukaiss.tetris.highscores;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.List;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.Score;

public class ScoreboardActivity extends Activity implements View.OnClickListener {

    private Scoreboard dao;

    private List<Score> scores;

    private GridView scoresView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.main_menu).setOnClickListener(this);

        dao = new Scoreboard(getApplicationContext());
        dao.open();
        scores = dao.listScores();

        scoresView = findViewById(R.id.scores);
        scoresView.setAdapter(new ScoreGridAdapter(this, scores));
        scoresView.setNumColumns(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.reset:
                dao.resetScoreboard();
                scores.clear();
                scoresView.invalidateViews();
                break;
            case R.id.main_menu:
                finish();
                break;
        }
    }

}
