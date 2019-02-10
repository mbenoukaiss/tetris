package mbenoukaiss.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setHorizontalSpacing(1);
        gridview.setVerticalSpacing(1);
        gridview.setNumColumns(10);
    }

}
