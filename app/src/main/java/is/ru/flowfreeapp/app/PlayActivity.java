package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yngvi on 8.9.2014.
 */
public class PlayActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

    /*    SharedPreferences settings = getSharedPreferences( "ColorPref", MODE_PRIVATE );

        int color = settings.getInt( "pathColor", Color.CYAN );
        Board board = (Board) findViewById( R.id.board );
        board.setColor( color ); */
    }

    public void buttonClick(View view) {
        Sound s = new Sound();
        s.playSound(this);
        TextView button = (TextView) view;
        int id = button.getId();

        if (id == R.id.buttonEasy) {
            startActivity(new Intent(this, EasyActivity.class));
        }
        /*else if ( id == R.id.buttonMedium ) {
            startActivity( new Intent( this, MediumActivity.class ) );
        }
        else if ( id == R.id.buttonHard) {
            startActivity( new Intent( this, HardActivity.class ) );
        }*/
    }

    public void backClick(View view) {
        Sound s = new Sound();
        s.playSound(this);
        ImageView backButton = (ImageView) view;
        int backId = backButton.getId();

        if (backId == R.id.backButton) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}