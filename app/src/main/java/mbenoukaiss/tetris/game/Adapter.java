package mbenoukaiss.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Adapter extends BaseAdapter {

    private final Context context;

    private final GridView view;

    private final Game game;

    private Handler handler = new Handler();

    private Runnable fallingTetrominoesClock = new Runnable() {
        @Override
        public void run() {
            game.processFallingTetromino();
            view.invalidateViews();

            if(!game.isLost())
                handler.postDelayed(this,500);
        }
    };

    public Adapter(Context context, GridView view, Game game) {
        this.context = context;
        this.game = game;
        this.view = view;

        handler.postDelayed(fallingTetrominoesClock, 1000);
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
        ImageView view = new ImageView(context);
        view.setAdjustViewBounds(true);

        if(game.isLost()) {
            Bitmap white = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            white.eraseColor(0xFFFFFFFF);

            view.setImageBitmap(white);
            return view;
        }

        Point position = new Point(
                offset % game.getGridSize().getWidth(),
                offset / game.getGridSize().getWidth()
        );

        Bitmap background = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        background.eraseColor(game.getSquareAt(position));

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(background);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }

}
