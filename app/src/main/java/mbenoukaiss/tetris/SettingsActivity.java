package mbenoukaiss.tetris;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import mbenoukaiss.tetris.game.Game;

public class SettingsActivity extends Activity implements View.OnClickListener {

    private TextView usernameView;

    private String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        usernameView = findViewById(R.id.username);

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        usernameView.setText(preferences.getString("username", getString(R.string.player)));
        size = preferences.getString("size", Game.DEFAULT_GRID_WIDTH + " x " + Game.DEFAULT_GRID_HEIGHT);

        int sizeId = Arrays.asList(getResources().getStringArray(R.array.gridsize_spinner)).indexOf(size);

        Spinner spinner = findViewById(R.id.gridsize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gridsize_spinner,
                R.layout.settings_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(sizeId > 0 ? sizeId : 0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = getResources().getStringArray(R.array.gridsize_spinner)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if(usernameView.getText().length() > 12) {
                    Toast.makeText(getApplicationContext(), R.string.long_username, Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
                editor.putString("size", size);
                if(!usernameView.getText().equals(getString(R.string.player)))
                    editor.putString("username", usernameView.getText().toString());

                editor.apply();
            case R.id.cancel:
                finish();
                break;
        }
    }

}
