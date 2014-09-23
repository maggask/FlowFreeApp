package is.ru.flowfreeapp.app;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by margretskristjansdottir on 19.9.14.
 */
public class Global {

    public ArrayList<Pack> mPacks;

    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}
}
