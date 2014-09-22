package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void buttonClick( View view ) {
        SharedPreferences settings = getSharedPreferences( "SwitchPref", MODE_PRIVATE );
        boolean soundOn = settings.getBoolean("soundSettings", false);
        boolean vibrateOn = settings.getBoolean("vibrationSettings", false);

        if(soundOn){
            Sound s = new Sound();
            s.playSound(this);
        }
        if(vibrateOn){
            Vibration v = new Vibration();
            v.vibrate(this);
        }
        else{
            Toast.makeText(this, "sound is not on", Toast.LENGTH_SHORT).show();
        }
        TextView button = (TextView) view;
        int id = button.getId();
        if ( id == R.id.buttonPlay ) {

        startActivity(new Intent(this, PlayActivity.class));
        }
        else if ( id == R.id.buttonSettings ) {
            startActivity( new Intent( this, SettingsActivity.class ) );
        }
        else if ( id == R.id.buttonInstructions ) {
            startActivity( new Intent( this, InstructionsActivity.class ) );
        }
    }


}
