package mbenoukaiss.tetris.pieces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    private int color;

    public SquareView(Context context, int color) {
        super(context);

        this.color = color;
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(color);
    }

}
