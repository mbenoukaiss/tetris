package mbenoukaiss.tetris;

import android.content.Context;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import mbenoukaiss.tetris.pieces.TetrominoView;

public class Adapter extends BaseAdapter {

    private final Context context;

    private Size gridSize;

    public Adapter(Context context, Size gridSize) {
        this.context = context;
        this.gridSize = gridSize;
    }

    @Override
    public int getCount() {
        return gridSize.getWidth() * gridSize.getHeight();
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
        TetrominoView view;

        if(convertView == null) {
            view = new TetrominoView(context, 0xFFFF0000);
            view.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            view.setPadding(0, 0, 0, 0);
        } else {
            view = (TetrominoView) convertView;
        }

        return view;
    }

}
