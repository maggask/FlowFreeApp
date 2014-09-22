package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void buttonClick(View view) {
        SharedPreferences settings = getSharedPreferences( "SwitchPref", MODE_PRIVATE );
        boolean soundOn = settings.getBoolean("soundSettings", false);

        if(soundOn) {
            Sound s = new Sound();
            s.playSound(this);
        }
        TextView button = (TextView) view;
        int id = button.getId();

        if (id == R.id.buttonEasy) {
            startActivity(new Intent(this, EasyActivity.class));
            this.finish ();
        }
        else if ( id == R.id.buttonMedium ) {
            startActivity( new Intent( this, MediumActivity.class ) );
        }
        else if ( id == R.id.buttonHard) {
            startActivity( new Intent( this, HardActivity.class ) );
        }
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
            startActivity(new Intent(this, MainActivity.class));
            this.finish ();
        }
    }
}