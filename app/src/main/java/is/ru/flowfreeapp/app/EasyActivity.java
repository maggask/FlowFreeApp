package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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

        final Global global = Global.getInstance();
        ArrayList<Puzzle> easyPackList = (ArrayList)global.mPacks.get(0).getPuzzles();
        ArrayList<String> strList = new ArrayList<String>();
        for (int i = 0; i < easyPackList.size(); i++) {
            int levelNumber = i + 1;
            strList.add("Level " + levelNumber);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, strList);

        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                global.difficulty = 0;
                global.level = itemPosition;

                // ListView Clicked item value
                String itemValue = (String)listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position : "+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
                goToGame(view);
            }

        });
    }

    public void goToGame(View view) {
        SharedPreferences settings = getSharedPreferences( "SwitchPref", MODE_PRIVATE );
        boolean soundOn = settings.getBoolean("soundSettings", false);

        if(soundOn){
            Sound s = new Sound();
            s.playSound(this);
        }
        startActivity(new Intent(this, GameActivity.class));
    }

    /*public void buttonClick(View view) {
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
    }*/

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
