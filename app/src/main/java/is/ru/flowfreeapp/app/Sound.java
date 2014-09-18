package is.ru.flowfreeapp.app;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
  * @(#)FlowFreeApp 18.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class Sound extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void playSound(Context context){
        MediaPlayer mp = new MediaPlayer();
        if (mp != null)
        {
            mp.release();
            mp = null;
        }
        try {
            mp = MediaPlayer.create(context, R.raw.plopp);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
