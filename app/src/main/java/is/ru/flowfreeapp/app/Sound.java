package is.ru.flowfreeapp.app;

import android.content.Context;
import android.media.MediaPlayer;

/**
  * @(#)FlowFreeApp 18.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class Sound {

    public void playSound(Context context) {

            MediaPlayer mediaPlayer = new MediaPlayer();

            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            try {
                mediaPlayer = MediaPlayer.create(context, R.raw.plopp);
                mediaPlayer.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
}
