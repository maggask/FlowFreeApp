package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
  * @(#)FlowFreeApp 12.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class EasyActivity extends Activity {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        // Get ListView object from xml
        listView = (ListView)findViewById(R.id.list);

        Global global = Global.getInstance();
        ArrayList<Pack> packList = global.mPacks;

        ArrayAdapter<Pack> adapter = new ArrayAdapter<Pack>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, packList);

        listView.setAdapter(adapter);
    }

    public void buttonClick(View view) {
        SharedPreferences settings = getSharedPreferences( "SwitchPref", MODE_PRIVATE );
        boolean soundOn = settings.getBoolean("soundSettings", false);

        if(soundOn){
            Sound s = new Sound();
            s.playSound(this);
        }
        ImageButton button = (ImageButton) view;
        int id = button.getId();
        if (id == R.id.game1) {
            startActivity(new Intent(this, GameActivity.class));
        }
        if (id == R.id.game2) {
            startActivity(new Intent(this, GameActivity.class));
        }
        if (id == R.id.game3) {
            startActivity(new Intent(this, GameActivity.class));
        }
        if (id == R.id.game4) {
            startActivity(new Intent(this, GameActivity.class));
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

        if (backId == R.id.backEasyButton) {
            startActivity(new Intent(this, PlayActivity.class));
            this.finish();
        }
    }
}
