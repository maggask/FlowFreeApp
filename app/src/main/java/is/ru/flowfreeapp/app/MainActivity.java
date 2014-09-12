package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import is.ru.flowfreeapp.app.R;

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
        TextView button = (TextView) view;
        int id = button.getId();
        if ( id == R.id.buttonPlay ) {
            startActivity( new Intent( this, PlayActivity.class ) );
        }
        else if ( id == R.id.buttonSettings ) {
            startActivity( new Intent( this, SettingsActivity.class ) );
        }
        else if ( id == R.id.buttonInstructions ) {
            startActivity( new Intent( this, InstructionsActivity.class ) );
        }
    }
}
