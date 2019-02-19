package mbenoukaiss.tetris.highscores;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.Score;

public class ScoreGridAdapter extends BaseAdapter {

    private final Context context;


    private final LayoutInflater inflater;

    private final List<Score> scores;

    public ScoreGridAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scores = scores;
    }

    @Override
    public int getCount() {
        return scores.size() * 2 + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text;

        if(convertView == null) {
            text = (TextView) inflater.inflate(R.layout.scoreboard_item, null);
        } else {
            text = (TextView) convertView;
            text.setTextSize(14);
        }

        if(position == 0) {
            text.setText(R.string.username);
            text.setTextSize(16);
        } else if(position == 1) {
            text.setText(R.string.score);
            text.setTextSize(16);
        } else {
            position -= 2;

            switch(position % 2) {
                case 0:
                    text.setText(scores.get(position / 2).getUsername());
                    break;
                case 1:
                    text.setText(String.valueOf(scores.get(position / 2).getScore()));
                    break;
            }
        }

        return text;
    }

}
