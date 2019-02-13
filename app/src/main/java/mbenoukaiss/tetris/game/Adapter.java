package mbenoukaiss.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import mbenoukaiss.tetris.R;

public class Adapter extends BaseAdapter {

    private final Context context;

    private final GridView view;

    private final Game game;

    public Adapter(Context context, GridView view, Game game) {
        this.context = context;
        this.game = game;
        this.view = view;
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

        ImageView view = new ImageView(context);
        view.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.square));
        view.setAdjustViewBounds(true);

        int filter = game.getSquareAt(position);
        view.setColorFilter(filter - 0x44000000);
        if(filter == 0xFFFFFFFF) view.setAlpha(0.25f);

        return view;
    }

}