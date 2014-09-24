package is.ru.flowfreeapp.app;

import android.app.Activity;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by margretskristjansdottir on 19.9.14.
 */
public class Global {

    public ArrayList<Pack> mPacks;
    public int level;
    public int difficulty;
    public boolean letters;
    public GameActivity gActivity;

    private static Global mInstance = new Global();

    public GameActivity getgActivity() {
        return gActivity;
    }

    public void setgActivity(GameActivity gActivity) {
        this.gActivity = gActivity;
    }

    public static Global getInstance() {
        return mInstance;
    }


    private Global() {}

}
