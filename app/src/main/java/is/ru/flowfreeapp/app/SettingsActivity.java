package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
  * @(#)FlowFreeApp 12.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch s1 = (Switch) findViewById(R.id.switch1);
        Switch s2 = (Switch) findViewById(R.id.switch2);
        Switch s3 = (Switch) findViewById(R.id.switch3);

        SharedPreferences settings = getSharedPreferences("SwitchPref", MODE_PRIVATE);
        boolean vibrationOn = settings.getBoolean("vibrationSettings", false);
        boolean soundOn = settings.getBoolean("soundSettings", false);
        boolean letterOn = settings.getBoolean("letterSettings", false);
        s1.setChecked(vibrationOn);
        s2.setChecked(soundOn);
        s3.setChecked(letterOn);
    }

        public void switchClick(View view) {
            Switch s = (Switch) view;
            if (s.getId() == R.id.switch1) {
                setPref("vibrationSettings", s.isChecked());
            }
            if (s.getId() == R.id.switch2) {
                setPref("soundSettings", s.isChecked());
            }
            if (s.getId() == R.id.switch3) {
                setPref("letterSettings", s.isChecked());
            }
        }

    public void setPref(String aSettings, boolean isOn) {
        SharedPreferences settings = getSharedPreferences("SwitchPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(aSettings, isOn);
        editor.commit();
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
