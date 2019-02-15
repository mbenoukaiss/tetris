package mbenoukaiss.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import mbenoukaiss.tetris.R;

public class Adapter extends BaseAdapter {

    private final Context context;

    private final Game game;

    private final Bitmap square;

    Adapter(Context context, Game game) {
        this.context = context;
        this.game = game;
        this.square = BitmapFactory.decodeResource(context.getResources(), R.drawable.square);
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

        ImageView view;

        if(convertView == null) {
            view = new ImageView(context);
            view.setImageBitmap(square);
            view.setAdjustViewBounds(true);
        } else {
            view = (ImageView) convertView;
            view.setAlpha(1.0f);
        }

        int filter = game.getSquareAt(position);
        view.setColorFilter(filter - 0x44000000);
        if(filter == 0xFFFFFFFF) view.setAlpha(0.25f);

        return view;
    }

}
