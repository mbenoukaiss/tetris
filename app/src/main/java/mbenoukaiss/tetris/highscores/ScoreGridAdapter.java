package mbenoukaiss.tetris.highscores;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mbenoukaiss.tetris.R;
import mbenoukaiss.tetris.Score;

public class ScoreGridAdapter extends BaseAdapter {

    private final Context context;

    private final List<Score> scores;

    private final Typeface pressStart;

    public ScoreGridAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.scores = scores;
        this.pressStart = context.getResources().getFont(R.font.press_start_2p);
    }

    @Override
    public int getCount() {
        return scores.size() * 2;
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
            text = new TextView(context);
            text.setTypeface(pressStart);
            text.setTextSize(12);
            text.setPadding(0, 16, 0, 16);

            //textalignment center doesn't work and gets reset to gravity for some reason
            text.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            text.setGravity(Gravity.CENTER);
        } else {
            text = (TextView) convertView;
        }


        switch(position % 2) {
            case 0:
                text.setText(scores.get(position / 2).getUsername());
                break;
            case 1:
                text.setText(String.valueOf(scores.get(position / 2).getScore()));
                break;
        }

        return text;
    }

}
