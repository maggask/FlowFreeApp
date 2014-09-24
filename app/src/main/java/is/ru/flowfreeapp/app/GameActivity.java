package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
  * @(#)FlowFreeApp 12.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class GameActivity extends Activity {

    int tempmoves;
    private GameAdapter gameAdapter = new GameAdapter(this);
    private Cursor mCursor;
    private SimpleCursorAdapter mCA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Global global = Global.getInstance();

        mCursor = gameAdapter.queryGameOnDiffLevel(global.difficulty, global.level);
        String fromDB = "";
        if (mCursor.moveToFirst()) {
            do {
                fromDB = fromDB + " " + mCursor.getString(4);
            } while (mCursor.moveToNext());
        }

        /*String cols[] = DbHelper.TableGamesCols;
        String from[] = { cols[4] };
        int to[] = { R.id.textNmoves };
        startManagingCursor( mCursor );
        mCA = new SimpleCursorAdapter(this, R.layout.activity_game, mCursor, from, to );*/

        SharedPreferences settings = getSharedPreferences("SwitchPref", MODE_PRIVATE);
        global.letters = settings.getBoolean("letterSettings", false);

        TextView levelTextView = new TextView(this);
        levelTextView = (TextView)findViewById(R.id.levelNumber);

        levelTextView.setText(Integer.toString(global.level + 1));

        TextView bestMoveTextView = new TextView(this);
        bestMoveTextView = (TextView)findViewById(R.id.bestNmoves);

        bestMoveTextView.setText(fromDB);
    }

    public void backClick(View view) {
        SharedPreferences settings = getSharedPreferences("SwitchPref", MODE_PRIVATE);
        boolean soundOn = settings.getBoolean("soundSettings", false);

        if (soundOn) {
            Sound s = new Sound();
            s.playSound(this);
        }

        ImageView backButton = (ImageView) view;
        int backId = backButton.getId();

        if (backId == R.id.backButton) {
            super.finish();
        }
    }

}
