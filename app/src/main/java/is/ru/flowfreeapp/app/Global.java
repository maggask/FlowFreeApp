package is.ru.flowfreeapp.app;

import java.util.List;

/**
 * Created by margretskristjansdottir on 19.9.14.
 */
public class Global {

    public List<Pack> mPacks;

    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}
}
