package mbenoukaiss.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.util.Size;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new Adapter(this, new Size(10, 15)));
        gridview.setHorizontalSpacing(1);
        gridview.setVerticalSpacing(1);
        gridview.setNumColumns(10);

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
            gridview.invalidateViews();
        });
    }
}
