package mbenoukaiss.tetris.highscores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mbenoukaiss.tetris.Score;

public class Scoreboard extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String USERNAME_COLUMN = "username";
    private static final String POINTS_COLUMN = "points";
    private static final String DATE_COLUMN = "date";

    private SQLiteDatabase db;

    public Scoreboard(Context context) {
        super(context, "tetris", null, VERSION);
    }

    @Override
    public synchronized void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE score (" +
                "username TEXT, " +
                "points INT, " +
                "date DATE);");
    }

    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE scores ;");
        onCreate(db);
    }

    public synchronized void open() {
        db = getWritableDatabase();
    }

    public synchronized void close() {
        db.close();
    }

    public synchronized void insertScore(Score score) {
        ContentValues values = new ContentValues();
        values.put(USERNAME_COLUMN, score.getUsername());
        values.put(POINTS_COLUMN, score.getScore());
        values.put(DATE_COLUMN, score.getDate());

        db.insert("score", null, values);
    }

    public synchronized int highestScore() {
        int score = -1;

        Cursor c = db.rawQuery("SELECT MAX(points) FROM score", new String[0]);

        if(c.getCount() == 1) {
            c.moveToFirst();
            score = c.getInt(0);
        } c.close();

        return score;
    }

    public synchronized ArrayList<Score> listScores() {
        ArrayList<Score> scores = new ArrayList<>();
        Cursor c = db.query(
                "score",
                new String[]{USERNAME_COLUMN, POINTS_COLUMN, DATE_COLUMN},
                null, null, null, null,
                POINTS_COLUMN + " DESC");

        if (c.getCount() > 0) {
            c.moveToFirst();
            do
                scores.add(new Score(c.getString(0), c.getInt(1), c.getString(2)));
            while (c.moveToNext());
        } c.close();

        return scores;
    }

    public synchronized void resetScoreboard() {
        db.execSQL("DELETE FROM score");
    }

}
