package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by margretskristjansdottir on 22.9.14.
 */
public class HardActivity extends Activity {

    ListView listView;
    private GameAdapter gameAdapter = new GameAdapter(this);
    private Cursor mCursor;
    private SimpleCursorAdapter mCA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);

        // Get ListView object from xml
        listView = (ListView)findViewById(R.id.list);

        final Global global = Global.getInstance();

        mCursor = gameAdapter.queryGamesByDifficulty(2);
        String[] cols = DbHelper.TableGamesCols;
        String[] from = { cols[1], cols[2], cols[3], cols[4] };
        int to[] = { android.R.id.text1, android.R.id.text1, android.R.id.text1, android.R.id.text1 };
        mCA = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mCursor, from, to);

        mCA.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == 1) {
                    TextView textView = (TextView)view;
                    textView.setText("Level " + Integer.toString(cursor.getInt(columnIndex) + 1));
                    return true;
                }
                else if(columnIndex == 2) {
                    TextView textView = (TextView)view;
                    if(cursor.getInt(columnIndex) == 1)
                        textView.setTextColor(Color.GREEN);
                    else
                        textView.setTextColor(Color.GRAY);

                    return true;
                }
                return true;
            }
        });

        listView.setAdapter(mCA);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                global.difficulty = 2;
                global.level = itemPosition;

                goToGame(view);
            }

        });
    }

    public void goToGame(View view) {
        SharedPreferences settings = getSharedPreferences( "SwitchPref", MODE_PRIVATE );
        boolean soundOn = settings.getBoolean("soundSettings", false);

        if (soundOn) {
            Sound s = new Sound();
            s.playSound(this);
        }

        startActivity(new Intent(this, GameActivity.class));
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

        if (backId == R.id.backHardButton) {
            startActivity(new Intent(this, PlayActivity.class));
            this.finish();
        }
    }
}
