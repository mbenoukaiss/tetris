package mbenoukaiss.tetris.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import mbenoukaiss.tetris.MainActivity;
import mbenoukaiss.tetris.R;

public class LostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost);

        findViewById(R.id.main_menu).setOnClickListener(v -> {
            Intent intent = new Intent(LostActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
