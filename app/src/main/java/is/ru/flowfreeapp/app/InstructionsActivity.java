package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
  * @(#)FlowFreeApp 12.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class InstructionsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
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
