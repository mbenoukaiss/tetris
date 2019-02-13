package mbenoukaiss.tetris.highscores;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import mbenoukaiss.tetris.R;

public class ScoreboardActivity extends Activity implements View.OnClickListener {

    private Scoreboard dao;

    private ListView scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.main_menu).setOnClickListener(this);

        dao = new Scoreboard(getApplicationContext());
        dao.open();

        scores = findViewById(R.id.scores_list);
        scores.setAdapter(new ArrayAdapter<>(this, R.layout.scoreboard_item, dao.listScores()));
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
                scores.invalidateViews();
                break;
            case R.id.main_menu:
                finish();
                break;
        }
    }
}
