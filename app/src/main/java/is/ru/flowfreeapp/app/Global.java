package is.ru.flowfreeapp.app;

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

    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}
}
