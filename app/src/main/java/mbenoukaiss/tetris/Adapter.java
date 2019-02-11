package mbenoukaiss.tetris;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import mbenoukaiss.tetris.pieces.SquareView;

public class Adapter extends BaseAdapter {

    private final Context context;

    private final GridView view;

    private final Game game;

    private Handler handler = new Handler();

    private Runnable fallingTetrominoesClock = new Runnable() {
        @Override
        public void run() {
            game.processFallingTetrominoes();
            view.invalidateViews();

            handler.postDelayed(this,2000);
        }
    };

    public Adapter(Context context, GridView view, Game game) {
        this.context = context;
        this.game = game;
        this.view = view;

        handler.postDelayed(fallingTetrominoesClock, 2000);
    }

    @Override
    public int getCount() {
        return game.getGridSize().getWidth() * game.getGridSize().getHeight();
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
    public View getView(int offset, View convertView, ViewGroup parent) {
        Point position = new Point(
                offset % game.getGridSize().getWidth(),
                offset / game.getGridSize().getWidth()
        );

        SquareView view = game.getSquareAt(position);
        view.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
        view.setPadding(0, 0, 0, 0);

        return view;
    }

}
