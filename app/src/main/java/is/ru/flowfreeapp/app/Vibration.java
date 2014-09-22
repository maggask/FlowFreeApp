package is.ru.flowfreeapp.app;

import android.content.Context;
import android.os.Vibrator;

/**
  * @(#)FlowFreeApp 22.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class Vibration {
    public void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }
}
